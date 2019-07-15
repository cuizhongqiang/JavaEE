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
 * 承兑
 */
@Entity
@Table(name = "acceptance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Acceptance extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 原币金额
	 */
	private Double originalCurrency;
	
	/**
	 * 押汇天数
	 */
	private Integer days;
	
	/**
	 * 押汇到期日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date documentaryBillsDate;
	
	/**
	 * 付汇/押汇金额
	 */
	private Double acceptanceMoney;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 实际付汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date actualDate;
	
	/**
	 * 银行
	 */
	private String bank;
	
	/**
	 * 实际汇率
	 */
	private Double actualRate;
	
	/**
	 * 客户汇率
	 */
	private Double customerRate;
	
	/**
	 * 折算成人民币
	 */
	private Double rmb;
	
	/**
	 * 实际手续费
	 */
	private Double actualPoundage;
	
	/**
	 * 客户手续费
	 */
	private Double customerPoundage;
	
	/**
	 * 押汇利率
	 */
	private Double documentaryBillsRate;
	
	/**
	 * 利息
	 */
	private Double interest;
	
	/**
	 * 类型 0-付汇，1-押汇
	 */
	private Integer type;
	
	/**
	 * 状态
	 */
	private String state = "草稿";

	@Column(name = "INVOICE_NO", nullable = false)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "DOCUMENTARY_BILLS_DATE")
	public Date getDocumentaryBillsDate() {
		return documentaryBillsDate;
	}

	public void setDocumentaryBillsDate(Date documentaryBillsDate) {
		this.documentaryBillsDate = documentaryBillsDate;
	}

	@Column
	public Double getAcceptanceMoney() {
		return acceptanceMoney;
	}

	public void setAcceptanceMoney(Double acceptanceMoney) {
		this.acceptanceMoney = acceptanceMoney;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DOCUMENTARY_BILLS_RATE")
	public Double getDocumentaryBillsRate() {
		return documentaryBillsRate;
	}

	public void setDocumentaryBillsRate(Double documentaryBillsRate) {
		this.documentaryBillsRate = documentaryBillsRate;
	}

	@Column
	public Double getRmb() {
		return rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "ORIGINAL_CURRENCY")
	public Double getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Double originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Column(name = "ACTUAL_DATE")
	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	@Column(name = "ACTUAL_RATE")
	public Double getActualRate() {
		return actualRate;
	}

	public void setActualRate(Double actualRate) {
		this.actualRate = actualRate;
	}

	@Column(name = "CUSTOMER_RATE")
	public Double getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(Double customerRate) {
		this.customerRate = customerRate;
	}

	@Column(name = "ACTUAL_POUNDAGE")
	public Double getActualPoundage() {
		return actualPoundage;
	}

	public void setActualPoundage(Double actualPoundage) {
		this.actualPoundage = actualPoundage;
	}

	@Column(name = "CUSTOMER_POUNDAGE")
	public Double getCustomerPoundage() {
		return customerPoundage;
	}

	public void setCustomerPoundage(Double customerPoundage) {
		this.customerPoundage = customerPoundage;
	}

	@Column
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Integer getType() {
		return type;
	}

	@Column
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}