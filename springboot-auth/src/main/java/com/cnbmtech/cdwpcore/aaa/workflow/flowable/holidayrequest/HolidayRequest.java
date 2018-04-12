/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.holidayrequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import com.alibaba.druid.util.StringUtils;
import com.cnbmtech.cdwpcore.aaa.msg.WebMessage;
import com.cnbmtech.cdwpcore.aaa.utils.BeanConverter;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance.AuthSrvProcessUtils;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance.FormApprove;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowUtils;

public class HolidayRequest implements WorkflowConstants {

	public static void main(String[] args) {
		System.out.println(processDefinitionFilePath);
		deploy();
		// View2(listProcessDefinitions());
		System.out.println(BeanConverter.json(WorkflowUtils.listProcessDefinitionIds(processDefinitionKey)));
		// processDefinition(processDefinitionKey);
		System.out.println(BeanConverter.json(WorkflowUtils.processDefinition(processDefinitionKey)));
		System.out.println(BeanConverter.json(listTasks("managers")));
		// WorkflowUtils.cleanProcessDefinitions(processDefinitionKey);
		// execute();
	}

	public final static String processDefinitionFilePath = "processes/holiday-request.bpmn20.xml";
	public final static String processDefinitionKey = "HolidayRequest";

	public static void deploy() {
		WorkflowUtils.deploy(processDefinitionFilePath);
	}

	public static WebMessage listProcessDefinitions() {
		return new WebMessage("", WorkflowUtils.listProcessDefinitions(processDefinitionKey), "ok");
	}

	public static WebMessage submitForm(final FormHolidayRequest form) {
		final WebMessage msg = new WebMessage(form.employee, "form", "init");
		
		if(StringUtils.isEmpty(form.employee)){
			return msg;
		}
		
		final Map<String, Object> variables = BeanConverter.map(form);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
		System.out.println("getProcessInstanceId="+processInstance.getProcessInstanceId());
		//runtimeService.addParticipantUser(processInstance.getId(), userId);
		//Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		//taskService.setAssignee(task.getId(), form.employee);
		//taskService.setOwner(task.getId(), form.employee);
		//System.out.println("getStartUserId="+processInstance.getStartUserId());
		msg.setContent(form);
		msg.setStatus("success");
		return msg;
	}
	
	public static WebMessage listTasks(final String groupId) {
		final WebMessage msg = new WebMessage(groupId, "approveform", "init");
		final List<Task> alltasks = taskService.createTaskQuery().taskCandidateGroup(groupId).list();
		// final String json = BeanToMapUtil.list2json(alltasks);
		// msg.setContent(json);
		final List<Object> r = new ArrayList<Object>();
		for(Task task:alltasks){
			final String taskId = task.getId();
			r.add(AuthSrvProcessUtils.listTaskContent(taskId).getContent());
		}
		
		msg.setContent(r);
		msg.setStatus("ok");
		return msg;
	}
	
	public static WebMessage listHistory(final String userId) {
		return AuthSrvProcessUtils.listHistory(userId,processDefinitionKey);
	}
	
	public static WebMessage approveForm(final String userId,final FormApprove form) {
		final WebMessage msg = new WebMessage(userId, "approveform", "init");
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("approved", form.approved);
		taskService.setAssignee(form.taskId, userId);
		taskService.setOwner(form.taskId, userId);
		//taskService.claim(form.taskId, userId);
		taskService.complete(form.taskId, variables);
		msg.setContent(form);
		msg.setStatus("ok");
		return msg;
	}

	public static void execute() {

		// 模拟请假流程，输入相关的参数
		Scanner scanner = new Scanner(System.in);

		// 请假人
		System.out.println("你是谁?");
		String employee = scanner.nextLine();

		// 请假天数
		System.out.println("你要请几天假?");
		Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

		// 请假理由
		System.out.println("请假理由?");
		String description = scanner.nextLine();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employee", employee);
		variables.put("nrOfHolidays", nrOfHolidays);
		variables.put("description", description);

		// 构建流程运行参数，启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

		// 获取待办任务列表
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
		System.out.println("你有 " + tasks.size() + " 个待办任务:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i + 1) + ") " + tasks.get(i).getName());
		}

		// 任务选择
		System.out.println("请选择你要处理的任务ID?");
		int taskIndex = Integer.valueOf(scanner.nextLine());
		Task task = tasks.get(taskIndex - 1);
		Map<String, Object> processVariables = taskService.getVariables(task.getId());

		// 流程审批
		System.out.println(processVariables.get("employee") + " 要请 " + processVariables.get("nrOfHolidays")
				+ " 天假. 是否同意? 同意回复 y ");

		boolean approved = scanner.nextLine().toLowerCase().equals("y");
		variables = new HashMap<String, Object>();
		variables.put("approved", approved);
		taskService.complete(task.getId(), variables);

		// 审批历史数据
		List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc()
				.list();

		for (HistoricActivityInstance activity : activities) {
			System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
		}

		scanner.close();
	}

}