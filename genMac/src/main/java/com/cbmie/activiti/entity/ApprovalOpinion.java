package com.cbmie.activiti.entity;

import java.util.HashMap;
import java.util.Map;

public class ApprovalOpinion {
	
	/**
	 * 流程ID
	 */
	private String processInstanceId;
	
	/**
	 * 任务ID
	 */
	private String taskId;
	
	/**
	 * 跳转节点ID
	 */
	private String goId;
	
	/**
	 * 驳回节点ID
	 */
	private String backId;
	
	/**
	 * 业务ID
	 */
	private Long businessKey;
	
	/**
	 * 流程key
	 */
	private String processKey;
	
	/**
	 * 意见
	 */
	private String comments;
	
	/**
	 * email
	 */
	private Integer email;
	
	/**
	 * 短信
	 */
	private Integer sms;
	
	/**
	 * 传阅
	 */
	private String[] encyclic;
	
	/**
	 * 候选人(一个或多个)
	 */
	private String[] candidateUserIds;
	
	/**
	 * 候选人变量名称
	 */
	private String candidateVariableName;
	
	/**
	 * 表单
	 */
	private Map<String, String> formMap = new HashMap<String, String>();
	
	/**
	 * 登录名（手机端）
	 */
	private String loginName;
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, String> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, String> formMap) {
		this.formMap = formMap;
	}

	public String getGoId() {
		return goId;
	}

	public void setGoId(String goId) {
		this.goId = goId;
	}

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	public Long getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(Long businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getEmail() {
		return email == 1 ? true : false;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	public boolean getSms() {
		return sms == 1 ? true : false;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}

	public String[] getEncyclic() {
		return encyclic;
	}

	public void setEncyclic(String[] encyclic) {
		this.encyclic = encyclic;
	}

	public String[] getCandidateUserIds() {
		return candidateUserIds;
	}

	public void setCandidateUserIds(String[] candidateUserIds) {
		this.candidateUserIds = candidateUserIds;
	}

	public String getCandidateVariableName() {
		return candidateVariableName;
	}

	public void setCandidateVariableName(String candidateVariableName) {
		this.candidateVariableName = candidateVariableName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
