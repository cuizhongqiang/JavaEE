package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;

public class TaskListenerGlobalSwitcher implements TaskListener, WorkflowConstants {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(TaskListenerGlobalSwitcher.class);

	@Override
	public void notify(final DelegateTask delegateTask) {
		final String pdId = delegateTask.getProcessDefinitionId();
		final String pdKey = pdId.split(":")[0];
		final String taskListener = "TaskListener" + pdKey;
		final String className = "com.cnbmtech.cdwpcore.aaa.workflow.flowable.listener." + taskListener;
		// final String classNameBackup =
		// "com.cnbmtech.cdwpcore.aaa.workflow.flowable.listener."+taskListener;
		logger.debug(className);

		final TaskListener obj = createObject(className);
		if (obj != null) {
			obj.notify(delegateTask);
		}else{
			logger.debug("please create class: " + className);
		}
	}

	public Class<?> getClass(final String className) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
			// clazz = ClassUtils.getClass(className);
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
		}
		return clazz;
	}

	public TaskListener createObject(final String className) {
		final Class<?> onwClass = getClass(className).asSubclass(TaskListener.class);
		TaskListener obj = null;
		try {
			obj = (TaskListener) onwClass.newInstance();
			//onwClass.cast(onwClass.newInstance());
			//obj = (TaskListener) BeanUtils.instantiate(onwClass);
			//obj = onwClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
