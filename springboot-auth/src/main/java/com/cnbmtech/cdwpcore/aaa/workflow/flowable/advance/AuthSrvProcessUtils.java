/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;

import com.cnbmtech.cdwpcore.aaa.msg.WebMessage;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowUtils;

public final class AuthSrvProcessUtils implements WorkflowConstants {
	
	public static WebMessage listProcessDefinitions(final String processDefinitionKey) {
		final List<ProcessDefinition> ProcessDefinitions = WorkflowUtils.listProcessDefinitions(processDefinitionKey);
		final WebMessage msg = new WebMessage("", ProcessDefinitions, "ok");
		return msg;
	}

	public static WebMessage listProcessDefinitionIds(final String processDefinitionKey) {
		final List<String> ProcessDefinitionIds = WorkflowUtils.listProcessDefinitionIds(processDefinitionKey);
		final WebMessage msg = new WebMessage("", ProcessDefinitionIds, "ok");
		return msg;
	}

	public static WebMessage listProcessDefinitionStructure(final String processDefinitionId) {
		repositoryService.createProcessDefinitionQuery();
		final WebMessage msg = new WebMessage("", WorkflowUtils.listProcessDefinitionStructure(processDefinitionId), "ok");
		return msg;
	}
	
	public static WebMessage listTaskContent(final String taskId) {
		final WebMessage msg = new WebMessage("", "approveform", "ok");
		final Map<String, Object> content = new HashMap<String, Object>();
		content.put("taskId", taskId);
		content.put("variables", taskService.getVariables(taskId));
		content.put("processInstanceId", taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId());
		// content.put("taskFormModel", taskService.getTaskFormModel(taskId));
		msg.setContent(content);
		return msg;
	}

	public static WebMessage listTaskContent(final String userId, final String processDefinitionKey) {

		// 审批历史数据
		final List<HistoricTaskInstance> tasks = new ArrayList<HistoricTaskInstance>();
		if (StringUtils.isEmpty(processDefinitionKey)) {
			tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
					.unfinished().orderByTaskCreateTime().desc().list());
		} else {
			tasks.addAll(historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefinitionKey)
					.taskAssignee(userId).unfinished().orderByTaskCreateTime().desc().list());
		}
		// final String json = BeanToMapUtil.list2json(activities);
		final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (HistoricTaskInstance task : tasks) {
			final Map<String, Object> result = new HashMap<String, Object>();
			result.put("taskId", task.getId());
			result.put("taskAssignee", task.getAssignee());
			result.put("taskOwner", task.getOwner());
			result.put("createTime", task.getCreateTime());
			result.put("claimTime", task.getClaimTime());
			result.put("endTime", task.getEndTime());
			result.put("durationInMillis", task.getDurationInMillis());
			result.put("dueDate", task.getDueDate());
			result.put("processInstanceId", task.getProcessInstanceId());

			// result.put("variables", taskService.getVariables(task.getId()));

			final Map<String, Object> variables = new HashMap<String, Object>();
			final List<HistoricVariableInstance> HistoricVariableInstances = historyService
					.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
			for (HistoricVariableInstance instance : HistoricVariableInstances) {
				variables.put(instance.getVariableName(), instance.getValue());
			}
			result.put("variables", variables);
			results.add(result);
		}
		final WebMessage msg = new WebMessage(userId, results, "ok");
		return msg;
	}

	public static WebMessage listHistory(final String userId, final String processDefinitionKey) {
		// 审批历史数据
		final List<HistoricTaskInstance> tasks = new ArrayList<HistoricTaskInstance>();
		if (StringUtils.isEmpty(processDefinitionKey)) {
			tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
					.orderByHistoricTaskInstanceEndTime().desc().list());
		} else {
			tasks.addAll(historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefinitionKey)
					.taskAssignee(userId).orderByHistoricTaskInstanceEndTime().desc().list());
		}

		// final String json = BeanToMapUtil.list2json(activities);
		final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (HistoricTaskInstance task : tasks) {
			final Map<String, Object> result = new HashMap<String, Object>();
			result.put("taskId", task.getId());
			result.put("taskAssignee", task.getAssignee());
			result.put("taskOwner", task.getOwner());
			result.put("createTime", task.getCreateTime());
			result.put("claimTime", task.getClaimTime());
			result.put("endTime", task.getEndTime());
			result.put("durationInMillis", task.getDurationInMillis());
			result.put("dueDate", task.getDueDate());
			result.put("processInstanceId", task.getProcessInstanceId());

			// result.put("variables", taskService.getVariables(task.getId()));

			final Map<String, Object> variables = new HashMap<String, Object>();
			final List<HistoricVariableInstance> HistoricVariableInstances = historyService
					.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
			for (HistoricVariableInstance instance : HistoricVariableInstances) {
				variables.put(instance.getVariableName(), instance.getValue());
			}
			result.put("variables", variables);

			results.add(result);
		}
		final WebMessage msg = new WebMessage(userId, results, "ok");
		return msg;
	}

	public static WebMessage listProcessDefinitionIds() {
		final WebMessage msg = new WebMessage("", WorkflowUtils.listProcessDefinitions(), "ok");
		return msg;
	}
}
