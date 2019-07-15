package com.cbmie.genMac.financial.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 费用
 */
@Entity
@Table(name = "expense")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Expense extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 客户
	 */
	private Long customerId;
	
	/**
	 * 业务员
	 */
	private String salesman;

	/**
	 * 状态
	 */
	private String state = "草稿";

	/**
	 * 申请日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date applyDate;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 应付总金额
	 */
	private Double sumMoney;

	/**
	 * 财务实付日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payDate;

	/**
	 * 实付金额
	 */
	private Double payCurrency;

	/**
	 * 未付金额
	 */
	private Double oweCurrency;

	private List<ExpenseDetail> expenseDetail = new ArrayList<ExpenseDetail>();
	
	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "STATE", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "APPLY_DATE", nullable = false)
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "SUM_MONEY")
	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	@Column(name = "PAY_DATE")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "PAY_CURRENCY")
	public Double getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(Double payCurrency) {
		this.payCurrency = payCurrency;
	}

	@Column(name = "OWE_CURRENCY")
	public Double getOweCurrency() {
		return oweCurrency;
	}

	public void setOweCurrency(Double oweCurrency) {
		this.oweCurrency = oweCurrency;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<ExpenseDetail> getExpenseDetail() {
		return expenseDetail;
	}

	public void setExpenseDetail(List<ExpenseDetail> expenseDetail) {
		this.expenseDetail = expenseDetail;
	}
}