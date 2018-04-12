/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cnbmtech.cdwpcore.aaa.module.account.AuthUtils;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.msg.WebMessage;
import com.cnbmtech.cdwpcore.aaa.utils.BeanConverter;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowImageUtils;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowUtils;

@RestController
@RequestMapping(value = "/workflow/advance/process", method = RequestMethod.GET)
public class AuthSrvProcess implements WorkflowConstants {
	@Autowired
	AuthUserRepository authUserRepository;

	@RequestMapping(value = "/cleanProcessDefinitions", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String cleanProcessDefinitions(final String processDefinitionKey) {
		WorkflowUtils.cleanProcessDefinitions(processDefinitionKey);
		return BeanConverter.json(processDefinitionKey);
	}

	@RequestMapping(value = "/deleteProcessDefinitionById", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String deleteProcessDefinitionById(final String processDefinitionId) {
		WorkflowUtils.deleteProcessDefinitionById(processDefinitionId);
		return BeanConverter.json(processDefinitionId);
	}

	@RequestMapping(value = { "/listProcessDefinitionKeys" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listProcessDefinitionKeys() {
		return BeanConverter.json(WorkflowUtils.listProcessDefinitionKeys());
	}
	
	@RequestMapping(value = { "/listProcessDefinitions" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listProcessDefinitions() {
		return BeanConverter.json(WorkflowUtils.listProcessDefinitions());
	}

	@RequestMapping(value = "/listProcessDefinitionIds", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listProcessDefinitionIds(final String processDefinitionKey) {
		if (StringUtils.isEmpty(processDefinitionKey)) {
			return BeanConverter.json(WorkflowUtils.listProcessDefinitionIds());
		}
		return BeanConverter.json(WorkflowUtils.listProcessDefinitionIds(processDefinitionKey));
	}

	@RequestMapping(value = { "/deleteDeployment" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String deleteProcess(final String deploymentId) {
		final WebMessage rtn = new WebMessage();
		repositoryService.deleteDeployment(deploymentId, true);
		rtn.setStatus("ok");
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = {
			"/deployProcessDefinitionByFile" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String deployProcessDefinitionByFile(@RequestParam("file") MultipartFile file) {
		final WebMessage rtn = new WebMessage();
		rtn.setStatus("ok");
		try {
			final Deployment deployment = repositoryService.createDeployment()
					.addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
			rtn.setContent(deployment);
		} catch (IOException e) {
			rtn.setContent(file.getOriginalFilename());
			rtn.setStatus("error");
			e.printStackTrace();
		}
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = { "/listTaskContent" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listTaskContent(final String taskId) {
		return BeanConverter.json(AuthSrvProcessUtils.listTaskContent(taskId));
	}

	@RequestMapping(value = { "/listHistory" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listHistory(final String processDefinitionKey) {
		final String userId = AuthUtils.username();
		return BeanConverter.json(AuthSrvProcessUtils.listHistory(userId, processDefinitionKey));
	}

	@RequestMapping(value = { "/getTasks" }, method = RequestMethod.GET)
	public String getTasks() {
		final String assignee = AuthUtils.username();
		final WebMessage rtn = new WebMessage();
		final List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
		rtn.setStatus("ok");
		rtn.setContent(tasks);
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = { "/getTasksByRoles" }, method = RequestMethod.GET)
	public String getTasksByRoles() {
		final List<Task> result;
		final String assignee = AuthUtils.username();
		String workflowRole = authUserRepository.findByUsername(assignee).getWorkflowrole();
		List<String> groups = new ArrayList<String>(Arrays.asList(workflowRole.split(",")));
		result = taskService.createTaskQuery().taskCandidateGroupIn(groups).list();
		final WebMessage rtn = new WebMessage();
		rtn.setStatus("ok");
		rtn.setContent(result);
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = { "/getTodoTasksNoticeByRoles" }, method = RequestMethod.GET)
	public String getTodoTasksNoticeByRoles() {
		final String assignee = AuthUtils.username();
		String workflowRole = authUserRepository.findByUsername(assignee).getWorkflowrole();
		List<String> groups = new ArrayList<String>(Arrays.asList(workflowRole.split(",")));
		long c = taskService.createTaskQuery().taskCandidateGroupIn(groups).count();

		final Map<String, Object> content = new HashMap<String, Object>();
		content.put("sum", String.valueOf(c));

		final List<Map<String, Object>> todoTasks = new ArrayList<Map<String, Object>>();

		List<ProcessDefinition> allProcesses = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		for (ProcessDefinition process : allProcesses) {
			String processId = process.getId();
			String processName = process.getName();
			String key = process.getKey();
			long todoTaskCount = taskService.createTaskQuery().taskCandidateGroupIn(groups).processDefinitionName(processName)
					.count();
			final Map<String, Object> todoTask = new HashMap<String, Object>();
			todoTask.put("id", processId);
			todoTask.put("name", processName);
			todoTask.put("key", key);
			todoTask.put("count", todoTaskCount);
			todoTasks.add(todoTask);
		}
		content.put("todoTasks", todoTasks);
		final WebMessage rtn = new WebMessage();
		rtn.setStatus("ok");
		rtn.setContent(content);
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = { "/approveTask" }, method = RequestMethod.GET)
	public String completeTask(@Valid FormApprove form) {
		final WebMessage rtn = new WebMessage();
		final Map<String, Object> map = BeanConverter.map(form);
		String taskId = form.getTaskId();
		taskService.complete(taskId, map);
		rtn.setStatus("ok");
		return BeanConverter.json(rtn);
	}

	@RequestMapping(value = "/images", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImages(final String processDefinitionKey) throws IOException {
		if (StringUtils.isEmpty(processDefinitionKey)) {
			return ResponseEntity.ok(null);
		}
		final InputStream in = WorkflowImageUtils.images2(processDefinitionKey);
		final byte[] body = new byte[in.available()];
		in.read(body);
		final ResponseEntity<byte[]> r = ResponseEntity.ok(body);
		return r;
	}

	@RequestMapping(value = { "/getProcessDefinitionNodes" }, method = RequestMethod.GET)
	public String getProcessDefinitionNodes() {
		final Map<String, List<String>> content = new HashMap<String, List<String>>();
		List<ProcessDefinition> allProcessDefinitions = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		for (ProcessDefinition processDefinition : allProcessDefinitions) {
			String processDefinitionId = processDefinition.getId();
			BpmnModel bm = repositoryService.getBpmnModel(processDefinitionId);
			List<org.flowable.bpmn.model.Process> allProcesses = bm.getProcesses();
			for (org.flowable.bpmn.model.Process process : allProcesses) {
				String processName = process.getName();
				Collection<FlowElement> allNodes = process.getFlowElements();
				List<String> allNodeNames = new ArrayList<String>();
				for (FlowElement node : allNodes) {
					if (node instanceof UserTask) {
						allNodeNames.add(node.getName());
					}
				}
				content.put(processName, allNodeNames);
			}
		}
		final WebMessage rtn = new WebMessage();
		rtn.setStatus("ok");
		rtn.setContent(content);
		return BeanConverter.json(rtn);
	}
}