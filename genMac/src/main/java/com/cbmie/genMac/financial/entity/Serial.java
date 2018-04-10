package com.cbmie.genMac.financial.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 流水
 */
@Entity
@Table(name = "serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Serial extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	private String serialNumber;

	/**
	 * 日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date serialDate;

	/**
	 * 银行
	 */
	private String bank;

	/**
	 * 银行账号
	 */
	private String bankNO;

	/**
	 * 水单抬头
	 */
	private Long customerId;

	/**
	 * 资金类别
	 */
	private String fundCategory;

	/**
	 * 银承到期日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date bankDeadline;

	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 资金来源
	 */
	private String fundSource;
	
	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 认领人备注
	 */
	private String comments;
	
	/**
	 * 水单类型
	 */
	private String serialCategory;
	
	/**
	 * 拆分状态 
	 */
	private String splitStatus;
	
	
	
	@Column(name = "SPLIT_STATUS")
	public String getSplitStatus() {
		return splitStatus;
	}

	public void setSplitStatus(String splitStatus) {
		this.splitStatus = splitStatus;
	}

	@Column(name = "SERIAL_CATEGORY")
	public String getSerialCategory() {
		return serialCategory;
	}

	public void setSerialCategory(String serialCategory) {
		this.serialCategory = serialCategory;
	}

	@Column(name = "SERIAL_NUMBER", nullable = false)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "SERIAL_DATE", nullable = false)
	public Date getSerialDate() {
		return serialDate;
	}

	public void setSerialDate(Date serialDate) {
		this.serialDate = serialDate;
	}

	@Column(name = "BANK", nullable = false)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "BANK_NO", nullable = false)
	public String getBankNO() {
		return bankNO;
	}

	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}

	@Column(name = "CUSTOMER_ID", nullable = false)
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "FUND_CATEGORY", nullable = false)
	public String getFundCategory() {
		return fundCategory;
	}

	public void setFundCategory(String fundCategory) {
		this.fundCategory = fundCategory;
	}

	@Column(name = "BANK_DEADLINE")
	public Date getBankDeadline() {
		return bankDeadline;
	}

	public void setBankDeadline(Date bankDeadline) {
		this.bankDeadline = bankDeadline;
	}

	@Column(name = "MONEY", nullable = false)
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "FUND_SOURCE", nullable = false)
	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	

}