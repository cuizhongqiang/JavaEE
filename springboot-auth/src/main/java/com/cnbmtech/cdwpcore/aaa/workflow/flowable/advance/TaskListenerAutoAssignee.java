package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;

public class TaskListenerAutoAssignee implements TaskListener, WorkflowConstants {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(TaskListenerAutoAssignee.class);

	@Override
	public void notify(final DelegateTask delegateTask) {
		final String pdId = delegateTask.getProcessDefinitionId();
		final String pdKey = pdId.split(":")[0];

		// final String classNameBackup =
		// "com.cnbmtech.cdwpcore.aaa.workflow.flowable.listener."+taskListener;
		logger.debug(pdKey);

		delegateTask.setAssignee(findAssignee(pdKey));
	}

	public String findAssignee(final String role){
		return "";
	}

}
