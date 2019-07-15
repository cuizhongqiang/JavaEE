/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowUtils implements WorkflowConstants {

	static Logger log = LoggerFactory.getLogger(WorkflowUtils.class);

	public static Deployment deploy(final String processDefinitionFilePath) {
		final Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource(processDefinitionFilePath).deploy();
		return deployment;
	}

	public static List<ProcessDefinition> listProcessDefinitions() {
		final List<ProcessDefinition> ProcessDefinitions = repositoryService.createProcessDefinitionQuery().list();
		return ProcessDefinitions;
	}

	public static List<Deployment> listDeployments() {
		final List<Deployment> Deployments = repositoryService.createDeploymentQuery().list();
		// 根据流程ID查找已经部署的流程
		Deployments.forEach(deployment -> log.debug(
				"部署:key=" + deployment.getKey() + ";id=" + deployment.getId() + ";name=" + deployment.getName()));
		return Deployments;
	}

	public static void cleanAllProcessDefinitions() {
		final List<ProcessDefinition> ProcessDefinitions = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition pd : ProcessDefinitions) {
			final String deploymentId = pd.getDeploymentId();
			repositoryService.deleteDeployment(deploymentId, true);
		}

		final List<Deployment> Deployments = repositoryService.createDeploymentQuery().list();
		for (Deployment pd : Deployments) {
			final String deploymentId = pd.getId();
			repositoryService.deleteDeployment(deploymentId, true);
		}

		log.debug("cleanOldProcessDefinitions finished!");
	}

	public static void cleanProcessDefinitions(final String processDefinitionKey) {
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions();
		for (ProcessDefinition pd : ProcessDefinitions) {
			if (pd.getKey().equals(processDefinitionKey)) {
				final String deploymentId = pd.getDeploymentId();
				repositoryService.deleteDeployment(deploymentId, true);
			}
		}
		log.debug("cleanProcessDefinitions finished!");
	}
	
	public static List<String> listProcessDefinitionStructure(final String processDefinitionId) {
		final List<String> l = new ArrayList<String>();
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions();
		for (ProcessDefinition pd : ProcessDefinitions) {
			if (pd.getId().equals(processDefinitionId)) {
				//pd;
			}
		}
		log.debug("cleanProcessDefinitions finished!");
		return l;
	}
	
	
	public static void deleteProcessDefinitionById(final String processDefinitionId) {
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions();
		for (ProcessDefinition pd : ProcessDefinitions) {
			if (pd.getId().equals(processDefinitionId)) {
				final String deploymentId = pd.getDeploymentId();
				repositoryService.deleteDeployment(deploymentId, true);
			}
		}
		log.debug("cleanProcessDefinitions finished!");
	}

	public static Map<String, String> processDefinition2Map(final String processDefinitionKey) {
		final Map<String, String> result = new HashMap<String, String>();
		// 根据流程ID查找已经部署的流程
		final ProcessDefinition oneProcessDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		if (oneProcessDefinition == null) {
			return result;
		}
		result.put("Key", oneProcessDefinition.getKey());
		result.put("Id", oneProcessDefinition.getId());
		result.put("Name", oneProcessDefinition.getName());
		result.put("Category", oneProcessDefinition.getCategory());
		result.put("Description", oneProcessDefinition.getDescription());
		result.put("DeploymentId", oneProcessDefinition.getDeploymentId());
		result.put("DiagramResourceName", oneProcessDefinition.getDiagramResourceName());
		return result;
	}

	public static ProcessDefinition processDefinition(final String processDefinitionKey) {
		// 根据流程ID查找已经部署的流程
		final ProcessDefinition oneProcessDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		return oneProcessDefinition;
	}

	public static String processDefinitionId(final String processDefinitionKey) {
		return processDefinition(processDefinitionKey).getId();
	}

	public static List<ProcessDefinition> listProcessDefinitions(final String processDefinitionKey) {
		final List<ProcessDefinition> ProcessDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).list();
		return ProcessDefinitions;
	}

	public static Set<String> listProcessDefinitionKeys() {
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions();
		final Set<String> ProcessDefinitionIds = new HashSet<String>();
		for(ProcessDefinition pd:ProcessDefinitions){
			ProcessDefinitionIds.add(pd.getKey());
		}
		return ProcessDefinitionIds;
	}
	
	public static List<String> listProcessDefinitionIds() {
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions();
		final List<String> ProcessDefinitionIds = new ArrayList<String>();
		for(ProcessDefinition pd:ProcessDefinitions){
			ProcessDefinitionIds.add(pd.getId());
		}
		return ProcessDefinitionIds;
	}
	
	public static List<String> listProcessDefinitionIds(final String processDefinitionKey) {
		final List<ProcessDefinition> ProcessDefinitions = listProcessDefinitions(processDefinitionKey);
		final List<String> ProcessDefinitionIds = new ArrayList<String>();
		for(ProcessDefinition pd:ProcessDefinitions){
			ProcessDefinitionIds.add(pd.getId());
		}
		return ProcessDefinitionIds;
	}

	public static void cleanProcessDefinitionsById(final String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	public static void showProcessDefinition(final String processDefinitionKey) {
		System.out.println(
				repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).count());
		// 根据流程ID查找已经部署的流程
		ProcessDefinition oneProcessDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		log.debug("发现定义的流程:" + oneProcessDefinition.getKey() + ";" + oneProcessDefinition.getId() + ";"
				+ oneProcessDefinition.getName());
	}

}