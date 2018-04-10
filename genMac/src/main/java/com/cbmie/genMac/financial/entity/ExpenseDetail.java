package com.cbmie.genMac.financial.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 费用明细
 */
@Entity
@Table(name = "expense_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ExpenseDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	/**
	 * 单据号
	 */
	private String documentNo;

	/**
	 * 付款子类型
	 */
	private String paymentChildType;
	
	/**
	 * 结算客户
	 */
	private String settlement;
	
	/**
	 * 收款情况
	 */
	private String receiptStatus;
	
	/**
	 * 支付方式
	 */
	private String payModel;
	
	/**
	 * 银行
	 */
	private String bankName;

	/**
	 * 账号
	 */
	private String bankNo;

	/**
	 * 金额
	 */
	private String money;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 父id
	 */
	private Long pid;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "DOCUMENT_NO")
	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "PAYMENT_CHILD_TYPE", nullable = false)
	public String getPaymentChildType() {
		return paymentChildType;
	}

	public void setPaymentChildType(String paymentChildType) {
		this.paymentChildType = paymentChildType;
	}
	
	@Column
	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	@Column(name = "RECEIPT_STATUS")
	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	@Column
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column
	public String getPayModel() {
		return payModel;
	}

	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}

	@Column
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	@Column
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}