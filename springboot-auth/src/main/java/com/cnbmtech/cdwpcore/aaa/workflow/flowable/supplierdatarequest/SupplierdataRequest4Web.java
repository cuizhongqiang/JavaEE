package com.cnbmtech.cdwpcore.aaa.workflow.flowable.supplierdatarequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;

public class SupplierdataRequest4Web implements WorkflowConstants {

	public static void main(String[] args) {
		final String deploymentFile = "testprocesses/holiday-request.bpmn20.xml";
		System.out.println(deploymentFile);
		// deploy(deploymentFile);
		listdeployment();
		final String processDefinitionKey = "holidayRequest";
		processDefinition(processDefinitionKey);
		execute();
	}

	public static Deployment deploy(final String filepath) {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("testprocesses/holiday-request.bpmn20.xml").deploy();
		return deployment;
	}

	public static void listdeployment() {
		List<ProcessDefinition> deployments = repositoryService.createProcessDefinitionQuery().list();
		// 根据流程ID查找已经部署的流程
		deployments.forEach(oneProcessDefinition -> System.out.println("部署的流程:key=" + oneProcessDefinition.getKey() + ";id="
				+ oneProcessDefinition.getId() + ";name=" + oneProcessDefinition.getName()));
	}

	public static void processDefinition(final String processDefinitionKey) {
		System.out.println(repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).count());
		// 根据流程ID查找已经部署的流程
		ProcessDefinition oneProcessDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		System.out.println("发现定义的流程:" + oneProcessDefinition.getKey() + ";"
				+ oneProcessDefinition.getId() + ";" + oneProcessDefinition.getName());
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

		// 流程引擎服务
		RuntimeService runtimeService = processEngine.getRuntimeService();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employee", employee);
		variables.put("nrOfHolidays", nrOfHolidays);
		variables.put("description", description);

		// 构建流程运行参数，启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

		// 获取待办任务列表
		TaskService taskService = processEngine.getTaskService();
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
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc()
				.list();

		for (HistoricActivityInstance activity : activities) {
			System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
		}

		scanner.close();
	}

}