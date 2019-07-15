
/**  
* @Title: WorkUtils.java
* @Package com.cnbmtech.cdwpcore.aaa.workflow
* @Description: TODO(用一句话描述该文件做什么)
* @author markzgwu
* @date 2017年12月29日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.workflow.flowable.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.history.HistoricActivityInstance;
//import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: WorkUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author markzgwu
 * @date 2017年12月29日
 *
 */

public final class WorkflowImageUtils implements WorkflowConstants {
	static final Logger logger = LoggerFactory.getLogger(WorkflowImageUtils.class);

	public static InputStream images(String processDefinitionKey) throws IOException {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task task : tasks) {
			String id = task.getProcessDefinitionId();
			boolean b = repositoryService.getProcessDefinition(id).getKey().equals("oneTaskProcess");
			if (b) {
				taskService.complete(task.getId());
			}
		}

		final String processDefinitionId = processInstance.getProcessDefinitionId();

		// 得到流程执行对象
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId())
				.list();
		// 得到正在执行的Activity的Id
		List<String> activityIds = new ArrayList<String>();
		for (Execution exe : executions) {
			List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
			activityIds.addAll(ids);
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds);
		return in;
	}

	public static InputStream imagesFromProcessInstance(final ProcessInstance processInstance) throws IOException {

		// 得到流程定义实体类

		final String processDefinitionId = processInstance.getProcessDefinitionId();

		// 得到流程执行对象
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId())
				.list();
		// 得到正在执行的Activity的Id
		List<String> activityIds = new ArrayList<String>();
		for (Execution exe : executions) {
			List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
			activityIds.addAll(ids);
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds);
		return in;
	}

	public static InputStream images4ProcessInstance(final String processInstanceId, final String processDefinitionId)
			throws IOException {

		// 得到流程执行对象
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
		// 得到正在执行的Activity的Id
		List<String> activityIds = new ArrayList<String>();
		for (Execution exe : executions) {
			List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
			activityIds.addAll(ids);
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		System.out.println(JSON.toJSONString(bpmnModel.getProcesses()));
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds);
		return in;
	}

	public static List<String> processInstanceIdByProcessDefinitionKey(final String processDefinitionKey) {
		List<String> processInstanceIds = new ArrayList<String>();
		runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list()
				.forEach(processInstance -> processInstanceIds.add(processInstance.getId()));
		return processInstanceIds;
	}

	public static InputStream images2(String processDefinitionKey) throws IOException {
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
//		String processInstanceId = processInstance.getId();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
//		taskService.complete(taskService.createTaskQuery().singleResult().getId());
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
		String processInstanceId = processInstance.getId();

		List<String> activeActivities = new ArrayList<String>();
		try {
			activeActivities = runtimeService.getActiveActivityIds(processInstanceId);
		} catch (Exception e) {
			List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processInstanceId).list();
			for (HistoricActivityInstance hai : haiList) {
				activeActivities.add(hai.getActivityId());
			}
		}
		logger.debug(JSON.toJSONString(activeActivities));
		BpmnModel bpmnModel = repositoryService.getBpmnModel(pd.getId());
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivities);
		return in;
	}

	public static String images2file(String processDefinitionKey) throws IOException {
		String filename = "D:\\watch.png";
		InputStream in = images(processDefinitionKey);
		FileOutputStream out = new FileOutputStream(filename);
		FileCopyUtils.copy(in, out);
		return filename;
	}

	public static String images2file2(String processDefinitionKey) throws IOException {
		String filename = "D:\\watch2.png";
		InputStream in = images2(processDefinitionKey);
		FileOutputStream out = new FileOutputStream(filename);
		FileCopyUtils.copy(in, out);
		return filename;
	}

	public static String images2file4processInstanceId(final String processInstanceId) {
		String filename = "D:\\watch1.png";
		String processDefinitionId = "oneTaskProcess:3:22505";

		InputStream in;
		try {
			in = images4ProcessInstance(processInstanceId, processDefinitionId);
			FileOutputStream out = new FileOutputStream(filename);
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filename;
	}

	public static void doTest() {
		// images2file("oneTaskProcess");
		List<String> ids = processInstanceIdByProcessDefinitionKey("oneTaskProcess");
		logger.debug(JSON.toJSONString(ids));
		ids.forEach(id -> images2file4processInstanceId(id));
	}

	public static void main(String[] args) {
		logger.debug("test");
		doTest();
		try {
			images2file2("oneTaskProcess");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
