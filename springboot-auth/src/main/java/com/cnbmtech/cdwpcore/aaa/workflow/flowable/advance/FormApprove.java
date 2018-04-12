package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import javax.validation.constraints.NotNull;

public final class FormApprove {
	@NotNull(message = "不能为空")
	public String taskId; // 任务id

	@NotNull
	public boolean approved; // 是否同意

	public String opinion; // 审批意见

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the opinion
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion
	 *            the opinion to set
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}
