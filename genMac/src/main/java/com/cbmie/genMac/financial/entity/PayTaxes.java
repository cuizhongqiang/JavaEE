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
 * 交税
 */
@Entity
@Table(name = "pay_taxes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PayTaxes extends BaseEntity implements java.io.Serializable {

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
	 * 汇率
	 */
	private Double rate;
	
	/**
	 * 原币金额
	 */
	private Double originalCurrency;
	
	/**
	 * 提单金额(人民币)
	 */
	private Double invoiceMoney;
	
	/**
	 * 税号
	 */
	private String taxNo;
	
	/**
	 * 关税税额
	 */
	private Double tax;

	/**
	 * 消费税额
	 */
	private Double saleTax;

	/**
	 * 增值税额
	 */
	private Double vat;
	
	/**
	 * 其它
	 */
	private Double otherTax;
	
	/**
	 * 总计税金
	 */
	private Double taxTotal;

	/**
	 * 申请日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date applyDate;
	
	/**
	 * 交税日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payDate;
	
	/**
	 * 是否委派货代
	 */
	private int delegaFreight = 0;
	
	/**
	 * 收款单位
	 */
	private String receivingUnit;
	
	/**
	 * 支付方式
	 */
	private String payModel;
	
	/**
	 * 交税银行
	 */
	private String bank;
	
	/**
	 * 交税账号
	 */
	private String account;
	
	/**
	 * 状态
	 */
	private String state = "草稿";
	
	private List<PayTaxesGoods> payTaxesGoods = new ArrayList<PayTaxesGoods>();

	@Column(name = "INVOICE_NO", nullable = false)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "ORIGINAL_CURRENCY")
	public Double getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Double originalCurrency) {
		this.originalCurrency = originalCurrency;
	}
	
	@Column(name = "INVOICE_MONEY")
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	@Column(name = "TAX_NO")
	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Column(name = "SALE_TAX")
	public Double getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(Double saleTax) {
		this.saleTax = saleTax;
	}

	@Column
	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	@Column(name = "OTHER_TAX")
	public Double getOtherTax() {
		return otherTax;
	}

	public void setOtherTax(Double otherTax) {
		this.otherTax = otherTax;
	}

	@Column(name = "TAX_TOTAL")
	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}
	
	@Column(name = "APPLY_DATE")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "PAY_MODEL")
	public String getPayModel() {
		return payModel;
	}

	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}

	@Column(name = "DELEGA_FREIGHT")
	public int getDelegaFreight() {
		return delegaFreight;
	}

	public void setDelegaFreight(int delegaFreight) {
		this.delegaFreight = delegaFreight;
	}

	@Column(name = "RECEIVING_UNIT")
	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}

	@Column
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<PayTaxesGoods> getPayTaxesGoods() {
		return payTaxesGoods;
	}

	public void setPayTaxesGoods(List<PayTaxesGoods> payTaxesGoods) {
		this.payTaxesGoods = payTaxesGoods;
	}

}