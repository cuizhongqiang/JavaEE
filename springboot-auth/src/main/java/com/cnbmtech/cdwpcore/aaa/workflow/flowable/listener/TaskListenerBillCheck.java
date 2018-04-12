package com.cnbmtech.cdwpcore.aaa.workflow.flowable.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;

public class TaskListenerBillCheck implements TaskListener, WorkflowConstants {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(TaskListenerBillCheck.class);

	@Override
	public void notify(final DelegateTask delegateTask) {
		String taskId = delegateTask.getId();
		String taskName = delegateTask.getName(); // 当前任务
		String processDefinitionId = delegateTask.getProcessDefinitionId();
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
		String processDefinitionName = processDefinition.getName();
		logger.debug("当前任务：{}", taskName);
		logger.debug("流程发起人：{}", taskService.getVariable(taskId, "starter"));
		logger.debug("流程中的单据编号：{}", taskService.getVariable(taskId, "billCode"));

		logger.debug("提交人：{}", taskService.getVariable(taskId, "completer"));
		logger.debug("是否同意：{}", taskService.getVariable(taskId, "approve"));
		logger.debug("审批意见：{}", taskService.getVariable(taskId, "opinion"));

		String workflowRoleName = processDefinitionName + ":" + taskName;
		delegateTask.addCandidateGroup(workflowRoleName);// NOTICE:本节点激活时动态实时将任务分配给角色："<流程名称>:<节点名称>"。在"系统管理/用户管理"中维护用户的workflowroles。
		logger.debug("任务分配给：{}", workflowRoleName);
		logger.debug("~~~~~~~~~~~~~~~~~~~~~~");
		// is null, must query by 'taskService.createTaskQuery().taskCandidateGroup'
		// List<IdentityLink> ill =
		// runtimeService.getIdentityLinksForProcessInstance(delegateTask.getProcessInstanceId());
		// is null, must query by 'taskService.createTaskQuery().taskCandidateGroup'
		// List<IdentityLink> ill2 = taskService.getIdentityLinksForTask(taskId);
		// is null, must query by 'taskService.createTaskQuery().taskCandidateGroup'
		// Set<IdentityLink> candidates = delegateTask.getCandidates();
	}
}