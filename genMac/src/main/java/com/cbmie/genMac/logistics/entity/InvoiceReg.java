package com.cbmie.genMac.logistics.entity;

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
 * 到单登记
 * @author czq
 */
@Entity
@Table(name = "invoice_reg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6341576388663483114L;
	
	/**
	 * 合同类型(0-代理进口,1-自营采购,2-内贸采购,3-内贸代理采购)
	 */
	private int contractType;
	
	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 业务员
	 */
	private String salesman;
	
	/**
	 * 供应商
	 */
	private String supplier;
	
	/**
	 * 贸易方式
	 */
	private String tradeWay;
	
	/**
	 * 是否冲销预付款
	 */
	private Integer revAdvPayment;
	
	/**
	 * 库存方式
	 */
	private String inventoryWay;
	
	/**
	 * 价格条款
	 */
	private String pricingTerms;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 对人民币汇率
	 */
	private Double rmbRate;
	
	/**
	 * 原币金额
	 */
	private Double originalCurrency;
	
	/**
	 * 提单金额（人民币）
	 */
	private Double invoiceMoney;

	/**
	 * 装运港
	 */
	private String transportPort;
	
	/**
	 * 目的港
	 */
	private String destinationPort;
	
	/**
	 * 到单日期
	 */
	private Date arriveDate;
	
	/**
	 * 付汇/承兑日期
	 */
	private Date acceptDate;
	
	/**
	 * 是否押汇
	 */
	private int ifDocumentaryBills;
	
	/**
	 * 押汇金额
	 */
	private Double documentaryBillsMoney;
	
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
	 * 财务确认
	 */
	private int financialConfirm;
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationNo;
	
	/**
	 * 报关单位
	 */
	private String customsDeclarationUnit;
	
	/**
	 * 到港日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date arrivalPortDate;
	
	/**
	 * 放行日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date letDate;
	
	/**
	 * 清关状态
	 */
	private String customsDeclarationState;
	
	/**
	 * 到单商品
	 */
	private List<InvoiceGoods> invoiceGoods = new ArrayList<InvoiceGoods>();
	
	@Column
	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	@Column(name = "INVOICE_NO", nullable = false, unique = true)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name = "PRICING_TERMS")
	public String getPricingTerms() {
		return pricingTerms;
	}

	public void setPricingTerms(String pricingTerms) {
		this.pricingTerms = pricingTerms;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "RMB_RATE")
	public Double getRmbRate() {
		return rmbRate;
	}

	public void setRmbRate(Double rmbRate) {
		this.rmbRate = rmbRate;
	}

	@Column(name = "ORIGINAL_CURRENCY")
	public Double getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Double originalCurrency) {
		this.originalCurrency = originalCurrency;
	}
	

	@Column(name = "TRANSPORT_PORT")
	public String getTransportPort() {
		return transportPort;
	}

	public void setTransportPort(String transportPort) {
		this.transportPort = transportPort;
	}

	@Column(name = "DESTINATION_PORT")
	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column(name = "TRADE_WAY")
	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	@Column(name = "REV_ADV_PAYMENT")
	public Integer getRevAdvPayment() {
		return revAdvPayment;
	}

	public void setRevAdvPayment(Integer revAdvPayment) {
		this.revAdvPayment = revAdvPayment;
	}

	@Column(name = "INVENTORY_WAY")
	public String getInventoryWay() {
		return inventoryWay;
	}

	public void setInventoryWay(String inventoryWay) {
		this.inventoryWay = inventoryWay;
	}
	
	@Column(name = "ARRIVE_DATE")
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	@Column(name = "CUSTOMS_DECLARATION_NO")
	public String getCustomsDeclarationNo() {
		return customsDeclarationNo;
	}

	public void setCustomsDeclarationNo(String customsDeclarationNo) {
		this.customsDeclarationNo = customsDeclarationNo;
	}

	@Column(name = "CUSTOMS_DECLARATION_UNIT")
	public String getCustomsDeclarationUnit() {
		return customsDeclarationUnit;
	}

	public void setCustomsDeclarationUnit(String customsDeclarationUnit) {
		this.customsDeclarationUnit = customsDeclarationUnit;
	}
	
	@Column(name = "ARRIVAL_PORT_DATE")
	public Date getArrivalPortDate() {
		return arrivalPortDate;
	}

	public void setArrivalPortDate(Date arrivalPortDate) {
		this.arrivalPortDate = arrivalPortDate;
	}

	@Column(name = "LET_DATE")
	public Date getLetDate() {
		return letDate;
	}

	public void setLetDate(Date letDate) {
		this.letDate = letDate;
	}

	@Column(name = "CUSTOMS_DECLARATION_STATE")
	public String getCustomsDeclarationState() {
		return customsDeclarationState;
	}

	public void setCustomsDeclarationState(String customsDeclarationState) {
		this.customsDeclarationState = customsDeclarationState;
	}

	@Column(name = "INVOICE_MONEY")
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	@Column(name = "ACCEPT_DATE")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Column(name = "IF_DOCUMENTARY_BILLS")
	public Integer getIfDocumentaryBills() {
		return ifDocumentaryBills;
	}

	public void setIfDocumentaryBills(int ifDocumentaryBills) {
		this.ifDocumentaryBills = ifDocumentaryBills;
	}

	@Column
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Column(name = "DOCUMENTARY_BILLS_DATE")
	public Date getDocumentaryBillsDate() {
		return documentaryBillsDate;
	}

	public void setDocumentaryBillsDate(Date documentaryBillsDate) {
		this.documentaryBillsDate = documentaryBillsDate;
	}

	@Column
	public Double getDocumentaryBillsMoney() {
		return documentaryBillsMoney;
	}

	public void setDocumentaryBillsMoney(Double documentaryBillsMoney) {
		this.documentaryBillsMoney = documentaryBillsMoney;
	}

	@Column
	public int getFinancialConfirm() {
		return financialConfirm;
	}

	public void setFinancialConfirm(int financialConfirm) {
		this.financialConfirm = financialConfirm;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<InvoiceGoods> getInvoiceGoods() {
		return invoiceGoods;
	}
	
	public void setInvoiceGoods(List<InvoiceGoods> invoiceGoods) {
		this.invoiceGoods = invoiceGoods;
	}

}
