/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.common;

import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.image.ProcessDiagramGenerator;

public interface WorkflowConstants {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	RuntimeService runtimeService = processEngine.getRuntimeService();
	RepositoryService repositoryService = processEngine.getRepositoryService();
	TaskService taskService = processEngine.getTaskService();
	ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
	ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
	HistoryService historyService = processEngine.getHistoryService();
	FormService formService = processEngine.getFormService();
	DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
	IdentityService identityService = processEngine.getIdentityService();
	ManagementService managementService = processEngine.getManagementService();
}
