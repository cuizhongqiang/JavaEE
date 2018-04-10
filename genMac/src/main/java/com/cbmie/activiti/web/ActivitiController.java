package com.cbmie.activiti.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.entity.Variable;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.common.utils.Reflections;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;

/**
 * 工作流核心
 * @author zhk
 */
@Controller
@RequestMapping(value = "workflow")
public class ActivitiController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
	
	/**流程列表展示*********************************************************************************************************************/
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "activiti/todoList";
	}
	
	/**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/task/todo/list")
    @ResponseBody
    public Map<String, Object> todoList(HttpServletRequest request) throws Exception {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getTodoList(request);
    	createPageByResult(page, result);
        return getEasyUIData(page);
    }
    
    /**
     * 读取运行中的流程跳转
     */
    @RequestMapping(value = "/task/running/go")
    public String goRunningProcessInstaces() {
        return "activiti/runningList";
    }
    
    /**
     * 读取运行中的流程
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/task/running/list")
    @ResponseBody
    public Map<String, Object> findRunningProcessInstaces(HttpServletRequest request) {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getRunningList(request);
        createPageByResult(page, result);
        return getEasyUIData(page);
    }
    
    /**
     * 已办任务
     */
    @RequestMapping(value = "/task/haveDone/list")
    @ResponseBody
    public Map<String, Object> haveDoneList(HttpServletRequest request) throws Exception {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getHaveDoneList(request);
    	createPageByResult(page, result);
        return getEasyUIData(page);
    }
    
    /**
     * 重新提交业务信息跳转
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "reApply/go/{pid}", method = RequestMethod.GET)
	public String reApply(@PathVariable("pid") String pid, Model model) throws ClassNotFoundException {
    	Map<String, String> map = activitiService.getKeys(pid);
    	String processKey = map.get("processKey");
    	Long businessKey = Long.parseLong(map.get("businessKey"));
    	
    	Class<?> clazz = activitiService.getWorkflowClass(processKey);
    	String entityName = clazz.getSimpleName();
    	
    	model.addAttribute(StringUtils.lowerFirst(entityName), activitiService.getReflectionObj(clazz, businessKey));
    	model.addAttribute("action", "update");
    	
    	String returnUrl = activitiService.getProcess(processKey).getForm();
    	if("".equals(returnUrl)){
    		returnUrl = "error/keyNotExist";
    	}
    	return returnUrl;
	}
    
    /**
     * 重新提交节点控制
     */
    @RequestMapping(value = "reApply", method = RequestMethod.GET)
	@ResponseBody
	public String passReApply(@RequestParam("pid") String processInstanceId, @RequestParam("id") String taskId, Model model) {
    	String comments = "重新提交申请";
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("1");
    	try {
            Map<String, Object> variables = var.getVariableMap();
            Map<String, Object> variableLocals = new HashMap<String, Object>();
            variableLocals.put("comments", comments);
            //验证是否是被退回，如果是被退回，将直接提交给退回者
            String activityId = activitiService.getReturnActivity(processInstanceId);
            activitiService.commitProcess(taskId, variables, activityId, variableLocals);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
	}
    
    /**
     * 流程废除节点控制
     */
    @RequestMapping(value = "abolishApply/{taskId}", method = RequestMethod.GET)
	@ResponseBody
	public String abolishApply(@PathVariable("taskId") String taskId) {
    	try {
            activitiService.endProcess(taskId, Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue());
            return "success";
        } catch (Exception e) {
            logger.error("abolish apply:", new Object[]{taskId, e});
            return "error";
        }
	}
    
    /**流程核心功能*********************************************************************************************************************/
    
    /**
     * 流程跟踪列表跳转
     */
    @RequestMapping(value = "/trace/{processInstanceId}", method = RequestMethod.GET)
	public String hitasklist(@PathVariable("processInstanceId") String processInstanceId, Model model) {
    	model.addAttribute("pid", processInstanceId);
		return "activiti/hitaskList";
	}
    
    /**
     * 流程跟踪列表json
     */
    @RequestMapping(value = "/trace/list/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String, Object> trace(HttpServletRequest request, @PathVariable("processInstanceId") String processInstanceId) {
    	Page<Map<String, Object>> page = getPage(request);
    	activitiService.getTraceInfo(processInstanceId, page);
        return getEasyUIData(page);
	}
    
    /**
     * 签收
     */
    @RequestMapping(value = "task/claim/{id}")
	@ResponseBody
	public String claim(@PathVariable("id") String id) {
		activitiService.doClaim(id);
		return "success";
	}
    
    /**
     * 查看明细跳转
     */
    @RequestMapping(value = "deal", method = RequestMethod.GET)
	public String deal(@RequestParam("processKey") String processKey, @RequestParam("businessKey") Long businessKey,
			RedirectAttributes rattr) {
    	String businessInfoReturnUrl = activitiService.getProcess(processKey).getDetail();
    	if ("".equals(businessInfoReturnUrl)) {
    		return "error/keyNotExist";
    	}
    	rattr.addAttribute("workflow", true);
    	return "redirect:/" + businessInfoReturnUrl + "/" + businessKey;
    }
    
    
    /**
     * 审批跳转
     * @param processInstanceId 流程id
     * @param taskId 任务id
     * @param businessKey 业务id
     * @param processKey 流程key
     * @param model
     * @return
     */
    @RequestMapping(value = "approval", method = RequestMethod.GET)
	public String approvalForm(@RequestParam("pid") String processInstanceId, 
			@RequestParam("id") String taskId, 
			@RequestParam("businessKey") Long businessKey, 
			@RequestParam("processKey") String processKey, Model model) {
    	
    	ApprovalOpinion approval = new ApprovalOpinion();
    	approval.setProcessInstanceId(processInstanceId);
    	approval.setTaskId(taskId);
    	approval.setBusinessKey(businessKey);
    	approval.setProcessKey(processKey);
    	List<ActivityImpl> backActivity = new ArrayList<ActivityImpl>();
    	List<ActivityImpl> goActivity = new ArrayList<ActivityImpl>();
    	List<ActivityImpl> nextActivity = new ArrayList<ActivityImpl>();
    	try {
    		backActivity = activitiService.findBackAvtivity(taskId);
    		goActivity = activitiService.findGoAvtivity(taskId);
    		nextActivity = activitiService.findNextAvtivity(taskId);
    	} catch (Exception e) {
    		logger.error(e.getLocalizedMessage(), e);
    	}
		model.addAttribute("approval", approval);
		model.addAttribute("backActivity", backActivity);
		model.addAttribute("goActivity", goActivity);
		model.addAttribute("nextActivity", nextActivity);
		model.addAttribute("form", activitiService.getFormData(taskId));
		
		return "activiti/approvalForm";
	}
    
    /**
     * 同意节点控制
     */
    @RequestMapping(value = "agree", method = RequestMethod.POST)
	@ResponseBody
	public String passComplete(@Valid @ModelAttribute @RequestBody ApprovalOpinion approval, Model model) {
    	String taskId = approval.getTaskId();
    	String comments = approval.getComments();
    	String activityId = approval.getGoId();
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("1");
    	for (Entry<String, String> entry : approval.getFormMap().entrySet()) {//把流程中的表单数据存入对象并持久化
    		Class<?> clazz = activitiService.getWorkflowClass(approval.getProcessKey());
    		Object obj = activitiService.getReflectionObj(clazz, approval.getBusinessKey());
    		Reflections.setFieldValue(obj, entry.getKey(), entry.getValue());
    		activitiService.updateReflectionObj(clazz, obj);
    	}
    	Map<String, Object> variables = var.getVariableMap();
    	if (StringUtils.isNotEmpty(approval.getCandidateVariableName())) {
    		variables.put(approval.getCandidateVariableName(), Arrays.asList(approval.getCandidateUserIds()));//指定候选人
    	}
    	try {
    		activitiService.inform(approval);
    		Map<String, Object> variableLocals = new HashMap<String, Object>();
            variableLocals.put("comments", comments);
            //验证是否是被退回，如果是被退回，将直接提交给退回者
            if (StringUtils.isEmpty(activityId)) {
            	activityId = activitiService.getReturnActivity(approval.getProcessInstanceId());
            }
            activitiService.commitProcess(taskId, variables, activityId, variableLocals);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
	}
    
    /**
     * 驳回/退回节点控制
     */
    @RequestMapping(value = "back/{flag}", method = RequestMethod.POST)
	@ResponseBody
	public String backComplete(@Valid @ModelAttribute @RequestBody ApprovalOpinion approval,
			@PathVariable("flag") int flag, Model model) {
    	String taskId = approval.getTaskId();
    	String comments = approval.getComments();
    	String activityId = approval.getBackId();
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("0");
    	try {
    		if (activityId != null) {// 获取驳回/退回节点受让人
				activitiService.getCandidateUserIds(approval, approval.getProcessInstanceId(),
						activitiService.findActivitiImpl(taskId, activityId));
        	}
    		activitiService.inform(approval);
            Map<String, Object> variables = var.getVariableMap();
            Map<String, Object> variableLocals = new HashMap<String, Object>();
            variableLocals.put("comments", comments);
            if (flag == 1) {//退回标志
            	variableLocals.put("return", true);
            }
            activitiService.commitProcess(taskId, variables, activityId, variableLocals);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return e.getMessage();
        }
	}
    
    /**
     * 撤回任务
     */
    @RequestMapping(value = "callBack", method = RequestMethod.GET)
    @ResponseBody
	public String callBackComplete(@RequestParam("currentTaskId") String currentTaskId,
			@RequestParam("activityId") String activityId, Model model) {
    	try {
    		activitiService.commitProcess(currentTaskId, null, activityId, null);
			return "success";
		} catch (Exception e) {
			logger.error("error on callBack complete task activityId {}", new Object[]{activityId, e});
            return e.getMessage();
		}
    }
    
    /**
     * 流程实例当前任务名称集合
     */
    @RequestMapping(value = "findCurrentTaskList/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findCurrentTaskList(@PathVariable("processInstanceId") String processInstanceId) {
    	List<String> nameList = new ArrayList<String>();
    	List<Task> list = activitiService.findTaskListByKey(processInstanceId, null);
    	for (Task task : list) {
    		nameList.add(task.getName());
    	}
    	return nameList;
    }
    
    /**
     * 强制生效
     */
    @RequestMapping(value = "effect/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public String effect(@PathVariable("processInstanceId") String processInstanceId) {
    	try {
			activitiService.endProcess(processInstanceId);
			return "success";
		} catch (Exception e) {
			logger.error("error on effect processInstance processInstanceId {}", new Object[]{processInstanceId, e});
            return e.getMessage();
		}
    }
    
    /**
     * 强制删除
     */
    @RequestMapping(value = "delete/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("processInstanceId") String processInstanceId) {
    	try {
			activitiService.deleteProcessInstance(processInstanceId, true);
			return "success";
		} catch (Exception e) {
			logger.error("error on delete processInstance processInstanceId {}", new Object[]{processInstanceId, e});
            return e.getMessage();
		}
    }
    
    /**
	 * 获取该节点最新的审批意见
	 */
    @RequestMapping(value = "getNewComments", method = RequestMethod.GET)
    @ResponseBody
    public String getNewComments(@RequestParam("processInstanceId") String processInstanceId,
    		@RequestParam("taskAssignee") String taskAssignee) {
		return activitiService.getNewComments(processInstanceId, taskAssignee);
    }
    
    /**
     * 获取流程中某个变量的值
     */
    @RequestMapping(value = "getVariableValue", method = RequestMethod.GET)
    @ResponseBody
    public String getVariableValue(@RequestParam("processInstanceId") String processInstanceId,
    		@RequestParam("variable") String variable) {
		return activitiService.getVariableValue(processInstanceId, variable);
    }
    
}
