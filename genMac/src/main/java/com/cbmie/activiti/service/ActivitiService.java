package com.cbmie.activiti.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.form.EnumFormType;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.entity.Variable;
import com.cbmie.activiti.entity.xml.Parameter;
import com.cbmie.activiti.entity.xml.Wf;
import com.cbmie.activiti.mail.SendMailService;
import com.cbmie.common.mapper.JaxbMapper;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.SimpleHibernateDao;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.IDGenerator;
import com.cbmie.common.utils.PropertyType;
import com.cbmie.common.utils.Reflections;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

@Service
@Transactional
public class ActivitiService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FormService formService;

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private SendMailService sendMailService;
	
	@Autowired
	private EncyclicService encyclicService;
	
	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

	/**
	 * 启动流程
	 */
	public ProcessInstance startWorkflow(Object obj, String processKey, Map<String, Object> variables, String userId) {
		ProcessInstance processInstance = null;
		try {
			// 初始化流程参数
			initParameter(variables, processKey);
			
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(userId);
			
			String businessKey = Reflections.invokeGetter(obj, "id").toString();
			processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
			String processInstanceId = processInstance.getId();
			Reflections.invokeSetter(obj, "processInstanceId", processInstanceId);
			
			logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { "testapply", businessKey, processInstanceId, variables });
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}

	/**
	 * 删除流程实例
	 */
	public boolean deleteProcessInstance(String processInstanceId, boolean flag) {
		// 获取所属流程下所有任务，并按开始时间倒序排列
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByTaskId().desc().list();
		if ((htiList.size() == 2 && htiList.get(0).getAssignee() == null) || flag) {
			if (runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().size() > 0) {
				runtimeService.deleteProcessInstance(processInstanceId, Enum.valueOf(AbolishReason.class, "CALLBACK").getValue());
			}
			historyService.deleteHistoricProcessInstance(processInstanceId);
			historyService.deleteHistoricTaskInstance(htiList.get(0).getId());
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Class<?> getWorkflowClass(String processKey) {
		String fullName = getProcess(processKey).getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return clazz;
	}
	
	/**
	 * 根据Class和id获取数据对象 利用配置文件和反射
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getReflectionObj(Class clazz, Long id) {
		SimpleHibernateDao simpleHibernateDao = new SimpleHibernateDao(sessionFactory, clazz);
		return simpleHibernateDao.getSession().get(clazz, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateReflectionObj(Class clazz, Object obj) {
		SimpleHibernateDao simpleHibernateDao = new SimpleHibernateDao(sessionFactory, clazz);
		simpleHibernateDao.getSession().update(clazz.getSimpleName(), obj);
	}

	/**
	 * 获取待办任务
	 */
	public List<Map<String, Object>> getTodoList(HttpServletRequest request) throws Exception {
		User user = UserUtil.getCurrentUser();
		String loginName = null;
		if (user == null) {
			loginName = request.getParameter("loginName");
		} else {
			loginName = user.getLoginName();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		// 已经签收的任务
		List<Task> todoList = taskService.createTaskQuery().taskAssignee(loginName).active()
				.orderByTaskCreateTime().desc().list();
		for (Task task : todoList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(result, task, processDefinition, request);
			singleTask.put("status", "待办");
		}

		// 等待签收的任务
		List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(loginName).active()
				.orderByTaskCreateTime().desc().list();
		for (Task task : toClaimList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(result, task, processDefinition, request);
			singleTask.put("status", "待签收");
		}
		return result;
	}

	/**
	 * 获取正在运行的流程
	 */
	public List<Map<String, Object>> getRunningList(HttpServletRequest request) {
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().desc().list();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (ProcessInstance processInstance : list) {
			String businessKey = processInstance.getBusinessKey();
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().list();
			Map<String, Object> singleProcess = new HashMap<String, Object>();
			String processKey = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId()).getKey();
			String businessInfo = getBusinessInfo(processKey, Long.parseLong(businessKey));

			singleProcess.put("name", repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName());
			singleProcess.put("processInstanceId", processInstance.getId());
			singleProcess.put("businessInfo", businessInfo);
			singleProcess.put("processDefinitionId", processInstance.getProcessDefinitionId());
			singleProcess.put("suspended", processInstance.isSuspended());
			singleProcess.put("applyPerson", runtimeService.getVariables(processInstance.getId()).get("init"));
			List<String> taskName = new ArrayList<String>();
			List<String> taskAssignee = new ArrayList<String>();
			for (Task task : tasks) {
				taskName.add(task.getName());
				taskAssignee.add(task.getAssignee());
			}
			singleProcess.put("curTaskCreateTime", DateUtils.formatDateTime(tasks.get(0).getCreateTime()));
			singleProcess.put("curTaskName", StringUtils.join(taskName, ","));
			singleProcess.put("curTaskAssignee", StringUtils.join(taskAssignee, ","));
			boolean flag = true;
			if (StringUtils.isNotBlank(request.getParameter("applyPerson"))) {
				flag = flag && String.valueOf(singleProcess.get("applyPerson")).contains(request.getParameter("applyPerson"));
			}
			if (StringUtils.isNotBlank(request.getParameter("businessInfo"))) {
				flag = flag && String.valueOf(singleProcess.get("businessInfo")).contains(request.getParameter("businessInfo"));
			}
			if (StringUtils.isNotBlank(request.getParameter("curTaskName"))) {
				flag = flag && String.valueOf(singleProcess.get("curTaskName")).contains(request.getParameter("curTaskName"));
			}
			if (flag) {
				result.add(singleProcess);
			}
		}
		return result;
	}

	/**
	 * 获取已办任务
	 */
	public List<Map<String, Object>> getHaveDoneList(HttpServletRequest request) {
		User user = UserUtil.getCurrentUser();
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processUnfinished()
				.taskAssignee(user.getLoginName()).finished().orderByHistoricTaskInstanceEndTime().desc().list();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (HistoricTaskInstance hti : list) {
			Map<String, Object> singleTask = new HashMap<String, Object>();

			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(hti.getProcessInstanceId()).singleResult();
			String processKey = repositoryService.getProcessDefinition(hti.getProcessDefinitionId()).getKey();
			String businessKey = hpi.getBusinessKey();
			String businessInfo = getBusinessInfo(processKey, Long.parseLong(businessKey));

			// 获取所属流程下所有任务，并按开始时间倒序排列
			List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(hpi.getId()).orderByTaskId().desc().list();
			// 当集合中第二个节点的处理人是“当前用户”且第一个"未签收"时可撤回
			if (hti.getTaskDefinitionKey().equals(htiList.get(1).getTaskDefinitionKey())
					&& htiList.get(0).getAssignee() == null) {
				singleTask.put("callBack", true);
			}

			singleTask.put("name", repositoryService.getProcessDefinition(hpi.getProcessDefinitionId()).getName());
			singleTask.put("processInstanceId", hpi.getId());
			singleTask.put("businessInfo", businessInfo);
			singleTask.put("activityId", hti.getTaskDefinitionKey());
			singleTask.put("taskId", hti.getId());
			singleTask.put("taskName", hti.getName());
			singleTask.put("currentTaskName", htiList.get(0).getName());
			singleTask.put("currentTaskId", htiList.get(0).getId());
			singleTask.put("currentActivityId", htiList.get(0).getTaskDefinitionKey());
			singleTask.put("startTime", DateUtils.formatDateTime(hti.getStartTime()));
			singleTask.put("endTime", DateUtils.formatDateTime(hti.getEndTime()));
			singleTask.put("processKey", processKey);
			singleTask.put("businessKey", businessKey);
			boolean flag = true;
			if (StringUtils.isNotBlank(request.getParameter("businessInfo"))) {
				flag = flag && String.valueOf(singleTask.get("businessInfo")).contains(request.getParameter("businessInfo"));
			}
			if (flag) {
				result.add(singleTask);
			}
		}
		return result;
	}

	/**
	 * 获取流程、业务相关key
	 */
	public Map<String, String> getKeys(String processInstanceId) {
		Map<String, String> map = new HashMap<String, String>();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String businessKey = pi.getBusinessKey();
		String processKey = repositoryService.getProcessDefinition(pi.getProcessDefinitionId()).getKey();
		map.put("processKey", processKey);
		map.put("businessKey", businessKey);
		return map;
	}

	/**
	 * 获取流程任务列表
	 */
	public void getTraceInfo(String processInstanceId, Page<Map<String, Object>> page) {
		HistoricTaskInstanceQuery tiq = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc();
		List<HistoricTaskInstance> listhistory = tiq.listPage(page.getFirst() - 1, page.getPageSize());
		page.setTotalCount(tiq.count());

		for (HistoricTaskInstance taskInstance : listhistory) {
			Map<String, Object> singleTask = new HashMap<String, Object>();
			String taskid = taskInstance.getId();
			HistoricVariableInstanceQuery hivarinstq = historyService.createHistoricVariableInstanceQuery();
			List<HistoricVariableInstance> hvi = hivarinstq.taskId(taskid).list();
			String comments = "";
			for (HistoricVariableInstance var : hvi) {
				if ("comments".equals(var.getVariableName())) {
					comments = (String) var.getValue();
				}
			}
			singleTask.put("id", taskInstance.getId());
			singleTask.put("name", taskInstance.getName());
			singleTask.put("assignee", taskInstance.getAssignee() == null ? "" : taskInstance.getAssignee());
			singleTask.put("startTime", DateUtils.formatDateTime(taskInstance.getStartTime()));
			singleTask.put("endTime", DateUtils.formatDateTime(taskInstance.getEndTime()));
			singleTask.put("comments", comments);
			page.getResult().add(singleTask);
		}
	}
	
	/**
	 * 根据流程定义id获取流程定义示例
	 */
	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
		if (processDefinition == null) {
			processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
		}
		return processDefinition;
	}

	/**
	 * 待办任务json打包
	 */
	private Map<String, Object> packageTaskInfo(List<Map<String, Object>> result, Task task,
			ProcessDefinition processDefinition, HttpServletRequest request) 
			throws Exception {
		Map<String, Object> singleTask = new HashMap<String, Object>();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.list().get(0);
		String businessKey = pi.getBusinessKey();

		String activityId = findTaskById(task.getId()).getTaskDefinitionKey();

		singleTask.put("id", task.getId());
		singleTask.put("activityId", activityId);
		singleTask.put("name", task.getName());
		singleTask.put("createTime", DateUtils.formatDateTime(task.getCreateTime()));
		singleTask.put("pdname", processDefinition.getName());
		singleTask.put("processKey", processDefinition.getKey());
		singleTask.put("pdversion", processDefinition.getVersion());
		singleTask.put("pid", task.getProcessInstanceId());
		singleTask.put("businessInfo", getBusinessInfo(processDefinition.getKey(), Long.parseLong(businessKey)));
		singleTask.put("businessKey", businessKey);
		boolean flag = true;
		if (StringUtils.isNotBlank(request.getParameter("businessInfo"))) {
			flag = flag && String.valueOf(singleTask.get("businessInfo")).contains(request.getParameter("businessInfo"));
		}
		if (flag) {
			result.add(singleTask);
		}
		return singleTask;
	}

	/**
	 * 获取业务信息
	 */
	public String getBusinessInfo(String processKey, Long id) {
		Class<?> clazz = getWorkflowClass(processKey);
		Object obj = getReflectionObj(clazz, id);
		return (String) Reflections.invokeGetter(obj, "summary");
	}

	public void doClaim(String taskId) {
		taskService.claim(taskId, UserUtil.getCurrentUser().getLoginName());
	}

	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {
		List<ActivityImpl> rtnList = null;
		rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>(), new ArrayList<ActivityImpl>());
		return reverList(rtnList);
	}

	/**
	 * 根据当前任务ID，查询可以跳转的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findGoAvtivity(String taskId) throws Exception {
		return iteratorGoActivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>(),
				new ArrayList<ActivityImpl>());
	}
	
	/**
	 * 根据当前任务ID，查询下一批任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findNextAvtivity(String taskId) throws Exception {
		return nextAvtivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>());
	}

	/**
	 * 中止流程(特权人直接审批通过等)
	 * 
	 * @param taskId
	 */
	public void endProcess(String taskId, String abolishReason) throws Exception {
		Variable var = new Variable();
    	var.setKeys(abolishReason);
    	var.setTypes("S");
    	var.setValues(abolishReason);
    	Map<String, Object> variables = var.getVariableMap();
		commitProcess(taskId, variables, "end", null);
	}

	/**
	 * 转办流程
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            被转办人Code
	 */
	public void transferAssignee(String taskId, String userCode) {
		taskService.setAssignee(taskId, userCode);
	}

	/**
	 * 会签操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param userCodes
	 *            会签人账号集合
	 * @throws Exception
	 * 
	 *             缺少核心逻辑
	 */
	public void jointProcess(String taskId, List<String> userCodes) throws Exception {
		for (String userCode : userCodes) {
			TaskEntity task = (TaskEntity) taskService.newTask(IDGenerator.generateID().toString());
			task.setAssignee(userCode);
			task.setName(findTaskById(taskId).getName() + "-会签");
			task.setProcessDefinitionId(findProcessDefinitionEntityByTaskId(taskId).getId());
			task.setProcessInstanceId(findProcessInstanceByTaskId(taskId).getId());
			task.setParentTaskId(taskId);
			task.setDescription("jointProcess");
			taskService.saveTask(task);
		}
	}

	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId, ActivityImpl currActivity,
			List<ActivityImpl> rtnList, List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		/*List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();*/
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			// 过滤分支
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			/*if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归  
				return rtnList;
            } else */if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			}
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		/*currActivity = filterNewestActivity(processInstance, tempList);*/
		if (tempList.size() == 1) {
			currActivity = tempList.get(0);
			/*// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity, "start");
			if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点*/
				rtnList.add(currActivity);
			/*} else {
				currActivity = findActivitiImpl(taskId, id);
			}*/

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		/*for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}*/

		return rtnList;
	}
	
	/**
	 * 迭代循环流程树结构，查询当前节点可跳转的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储跳转节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 跳转节点集合
	 */
	private List<ActivityImpl> iteratorGoActivity(String taskId, ActivityImpl currActivity,
			List<ActivityImpl> rtnList, List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> outgoingTransitions = currActivity.getOutgoingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			
			ActivityImpl activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			
			if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			}
		}

		if (tempList.size() == 1) {
			currActivity = tempList.get(0);
			rtnList.add(currActivity);
			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorGoActivity(taskId, currActivity, rtnList, tempList);
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorGoActivity(taskId, activityImpl, rtnList, tempList);
		}

		return rtnList;
	}
	
	/**
	 * 查询当前节点的下一用户节点
	 * @param taskId
	 * @return
	 */
	private List<ActivityImpl> nextAvtivity(String taskId, ActivityImpl currActivity, List<ActivityImpl> list) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> outgoingTransitions = currActivity.getOutgoingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			
			ActivityImpl activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			
			if ("parallelGateway".equals(type)) {// 并行路线
				parallelGateways.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			} else if ("userTask".equals(type)) {// 用户任务
				list.add(activityImpl);
			} else if (("endEvent".equals(type))) {// 结束节点
				list.add(activityImpl);
			}
		}
	
		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			nextAvtivity(taskId, activityImpl, list);
		}
	
		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			nextAvtivity(taskId, activityImpl, list);
		}
		
		return list;
	}

	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(findTaskById(taskId).getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new Exception("流程实例未找到!");
		}
		return processInstance;
	}

	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance, List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 查询指定任务节点的最新记录
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.activityType("userTask").processInstanceId(processInstance.getId()).activityId(activityId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}

	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行ID
	 * 
	 * @param activityImpl
	 *            当前节点
	 * @param str
	 *            start/end
	 * @return
	 */
	private String findParallelGatewayId(ActivityImpl activityImpl, String str) {
		List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					if (str.equals("start")) {
						return gatewayId.substring(0, gatewayId.lastIndexOf("_")) + "_start";
					} else if (str.equals("end")) {
						return gatewayId.substring(0, gatewayId.lastIndexOf("_")) + "_end";
					}
				}
			}
		}
		return null;
	}

	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isEmpty(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);

		return activityImpl;
	}

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	public List<Task> findTaskListByKey(String processInstanceId, String key) {
		if (key == null) {
			return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		}
		return taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();
	}

	/**
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程变量
	 * @param activityId
	 *            流程转向执行任务节点ID<br>
	 *            此参数为空，默认为提交操作
	 * @throws Exception
	 */
	public void commitProcess(String taskId, Map<String, Object> variables, String activityId,
			Map<String, Object> variableLocals) throws Exception {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		if (variableLocals == null) {
			variableLocals = new HashMap<String, Object>();
		}
		for (Entry<String, Object> entry : variableLocals.entrySet()) {
			taskService.setVariableLocal(taskId, entry.getKey(), entry.getValue());// 与任务ID绑定
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isEmpty(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}
	
	/**
	 * 结束流程
	 * @param processInstanceId 流程id
	 * @param activityId 跳转节点
	 * @param variables 流程存储参数
	 * @throws Exception
	 */
	public void endProcess(String processInstanceId) throws Exception {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task task : tasks) {
			turnTransition(task.getId(), "end", new HashMap<String, Object>());
		}
	}

	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
	/**
	 * 根据节点获取候选人(一个或多个)
	 */
	public String[] getCandidateUserIds(ApprovalOpinion approval, String processInstanceId, ActivityImpl... nextActivity) {
		List<String> candidateUserIds = new ArrayList<String>();
		for (ActivityImpl ai : nextActivity) {
			String type = (String) ai.getProperty("type");
			if (!"userTask".equals(type)) {continue;}
			TaskDefinition tdf = (TaskDefinition) ai.getProperties().get("taskDefinition");
			Set<Expression> exSet = tdf.getCandidateUserIdExpressions();
			if (exSet.size() == 0) {
				candidateUserIds.add(tdf.getAssigneeExpression().getExpressionText());
			} else {
				Iterator<Expression> exIt = exSet.iterator();
				while (exIt.hasNext()) {
					Expression ex = exIt.next();
					Pattern pattern = Pattern.compile("^\\$\\{(.+)\\}$");
					Matcher matcher = pattern.matcher(ex.getExpressionText());
					if (matcher.find()) {
						if (approval != null) {
							approval.setCandidateVariableName(matcher.group(1));
						}
						Object obj = runtimeService.getVariables(processInstanceId).get(matcher.group(1));
						if (obj instanceof List) {
							@SuppressWarnings("unchecked")
							List<String> listObj = (List<String>) obj;
							for (String str : listObj) {
								candidateUserIds.add(str);
							}
						} else {
							candidateUserIds.add(String.valueOf(obj));
						}
					} else {
						candidateUserIds.add(ex.getExpressionText());
					}
				}
			}
		}
		if (approval != null) {
			approval.setCandidateUserIds(candidateUserIds.toArray(new String[candidateUserIds.size()]));
		}
		return candidateUserIds.toArray(new String[candidateUserIds.size()]);
	}
	
	/**
	 * 初始化参数值
	 */
	private void initParameter(Map<String, Object> variables, String processKey) throws IOException {
		com.cbmie.activiti.entity.xml.Process process = getProcess(processKey);
		for (Parameter parameter : process.getParameter()) {
			Object objectValue = null;
			if (parameter.getType().equals("List")) {
				objectValue = Arrays.asList(parameter.getValue().split(","));
			} else {
				Class<?> targetType = Enum.valueOf(PropertyType.class, parameter.getType()).getValue();
				objectValue = ConvertUtils.convert(parameter.getValue(), targetType);
			}
			variables.put(parameter.getKey(), objectValue);
		}
	}
	
	/**
	 * 读取wf.xml下对应的流程
	 */
	public com.cbmie.activiti.entity.xml.Process getProcess(String processKey) {
		com.cbmie.activiti.entity.xml.Process returnProcess = null;
		try {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource("workflow.xml");
			InputStream is = resource.getInputStream();
			InputStreamReader isr= new InputStreamReader(is);
			BufferedReader br= new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			int last = sb.lastIndexOf("\r\n");
			Wf wf = JaxbMapper.fromXml(sb.toString().substring(0, last), Wf.class);
			List<com.cbmie.activiti.entity.xml.Process> processList = wf.getProcess();
			for (com.cbmie.activiti.entity.xml.Process process : processList) {
				if (process.getId().equals(processKey)) {
					returnProcess = process;
				}
			}
		} catch (IOException e) {
			logger.debug("Could not find {} process", processKey);
		}
		return returnProcess;
	}
	
	/**
	 * 获取该节点最新的审批意见
	 */
	public String getNewComments(String processInstanceId, String taskAssignee) {
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished().taskAssignee(taskAssignee)
				.orderByHistoricTaskInstanceEndTime().desc().list();
		HistoricTaskInstance hti = null;
		if (htiList.size() > 0) {
			hti = htiList.get(0);
		}
		String comments = "";
		// 获取comments变量
		if (hti != null) {
			HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstanceId).taskId(hti.getId()).variableName("comments").singleResult();
			comments = (String) hvi.getValue();
		}
		return comments;
	}
	
	/**
	 * 获取流程中某个变量的值
	 */
	public String getVariableValue(String processInstanceId, String variable) {
		HistoricVariableInstanceQuery hvi = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId).variableName(variable);
		return hvi.singleResult() == null ? "" : (String)hvi.singleResult().getValue();
	}
	
	/**
	 * 根据taskId获取form
	 */
	public List<Map<FormProperty, Object>> getFormData(String taskId) {
		List<Map<FormProperty, Object>> returnList = new ArrayList<Map<FormProperty, Object>>();
		List<FormProperty> list = formService.getTaskFormData(taskId).getFormProperties();
		for(FormProperty formProperty : list){
			FormType formType = formProperty.getType();
			if (formType instanceof EnumFormType) {
				EnumFormType enumFormType = (EnumFormType) formType;
				Map<FormProperty, Object> map = new HashMap<FormProperty, Object>();
				map.put(formProperty, enumFormType.getInformation("values"));
				returnList.add(map);
			}
		}
		return returnList;
	}
	
	/**
	 * 获取上一退回节点
	 */
	public String getReturnActivity(String processInstanceId) {
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished().orderByHistoricTaskInstanceEndTime().desc().list();
		HistoricTaskInstance hti = null;
		if (htiList.size() > 0) {
			hti = htiList.get(0);
		}
		String activityId = "";
		if (hti != null) {
			HistoricVariableInstanceQuery hvi = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstanceId).taskId(hti.getId()).variableName("return");
			if (hvi.singleResult() != null) {
				activityId = hti.getTaskDefinitionKey();
			}
		}
		return activityId;
	}
	
	/**
     * 通知（邮件、短信、传阅）
     */
    public void inform(ApprovalOpinion approval) {
		String businessInfo = getBusinessInfo(approval.getProcessKey(), approval.getBusinessKey());
		// 发送邮件
		sendMailService.sendMail(approval, businessInfo);
		// 传阅
		if (approval.getEncyclic() != null) {
			encyclicService.save(approval, businessInfo);
		}
    }
	
}
