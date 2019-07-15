package com.cnbmtech.cdwpcore.aaa.workflow.flowable.billcheck;

import javax.validation.constraints.NotNull;

public final class FormBillCheckStart {
	@NotNull(message = "不能为空")
	private String starter; // 流程发起人

	@NotNull(message = "不能为空")
	private String billCode; // 单据编号

	/**
	 * @return the starter
	 */
	public String getStarter() {
		return starter;
	}

	/**
	 * @param starter
	 *            the starter to set
	 */
	public void setStarter(String starter) {
		this.starter = starter;
	}

	/**
	 * @return the billCode
	 */
	public String getBillCode() {
		return billCode;
	}

	/**
	 * @param billCode
	 *            the billCode to set
	 */
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
}
