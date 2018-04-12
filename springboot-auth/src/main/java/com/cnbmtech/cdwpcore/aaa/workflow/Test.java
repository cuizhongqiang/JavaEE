
    /**  
    * @Title: Test.java
    * @Package com.cnbmtech.cdwpcore.aaa.worlflow
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月28日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.workflow;

import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;

import com.cnbmtech.cdwpcore.aaa.workflow.flowable.holidayrequest.HolidayRequest;

/**
    * @ClassName: Test
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月28日
    *
    */

public final class Test {
	
	/**
	    * @Title: main
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param args    参数
	    * @return void    返回类型
	    * @throws
	    */

	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		System.out.println(runtimeService);
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		System.out.println(repositoryService);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(HolidayRequest.processDefinitionKey);
		System.out.println(processInstance);
		
		TaskService taskService = processEngine.getTaskService();
		System.out.println(taskService);
		
		ManagementService managementService = processEngine.getManagementService();
		System.out.println(managementService);
		
		IdentityService identityService = processEngine.getIdentityService();
		System.out.println(identityService);
		
		HistoryService historyService = processEngine.getHistoryService();
		System.out.println(historyService);
		
		FormService formService = processEngine.getFormService();
		System.out.println(formService);
		
		DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
		System.out.println(dynamicBpmnService);

	}

}
