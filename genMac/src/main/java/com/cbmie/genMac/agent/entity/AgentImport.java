package com.cbmie.genMac.agent.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 * 代理进口
 * @author czq
 */
@Entity
@Table(name = "agent_import")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AgentImport extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -595260427222943439L;

	/**
	 * 我司单位
	 */
	private String myselfUnit;
	
	/**
	 * 客户
	 */
	private Long customerId;
	
	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
	 * 商品类型
	 */
	private String goodsType;
	
	/**
	 * 商品类型备注
	 */
	private String goodsTypeRemark;
	
	/**
	 * 业务员
	 */
	private String salesman;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 对人民币汇率
	 */
	private Double exchangeRateSelf;
	
	/**
	 * 对美元汇率
	 */
	private Double exchangeRateUS;
	
	/**
	 * 原币
	 */
	private Double originalCurrency;
	
	/**
	 * 人民币
	 */
	private Double rmb;
	
	/**
	 * 美元
	 */
	private Double dollar;
	
	/**
	 * 客户类型(新开发客户、长单老客户)
	 */
	private int customerType = 0;
	
	/**
	 * 客户名称(新开发客户)
	 */
	private String customerName;
	
	/**
	 * 注册时间(新开发客户)
	 */
	private Date registerDate;
	
	/**
	 * 注册资本(新开发客户)
	 */
	private Double registerCapital;
	
	/**
	 * 授信额度(长单老客户)
	 */
	private Double lineCredit;
	
	/**
	 * 目前敞口(长单老客户)
	 */
	private Double currentOpen;
	
	/**
	 * 已超期金额(长单老客户)
	 */
	private Double hasBeyond;
	
	/**
	 * 外合同名称
	 */
	private String foreignContractName;
	
	/**
	 * 外合同编号
	 */
	private String foreignContractNo;
	
	/**
	 * 外合同版本来源
	 */
	private int foreignVersionSource = 0;
	
	/**
	 * 外商
	 */
	private Long foreignId;
	
	/**
	 * 支付方式
	 */
	private String paymentMethod;
	
	/**
	 * 支付方式备注
	 */
	private String paymentMethodRemark;
	
	/**
	 * 价格条款
	 */
	private String priceTerms;
	
	/**
	 * 装期
	 */
	private Date shipmentDate;
	
	/**
	 * 争议管辖
	 */
	private String dispute;
	
	/**
	 * 内合同名称
	 */
	private String inlandContractName;
	
	/**
	 * 内合同版本来源
	 */
	private int inlandVersionSource = 0;
	
	/**
	 * 代理费
	 */
	private String agencyFee;
	
	/**
	 * 代理费备注
	 */
	private String agencyFeeRemark;
	
	/**
	 * 保证金
	 */
	private String margin;
	
	/**
	 * 项目要求
	 */
	private String projectClaim;
	
	/**
	 * 项目要求备注
	 */
	private String projectClaimRemark;
	
	/**
	 * 担保措施
	 */
	private int assurance = 0;
	
	/**
	 * 担保措施备注
	 */
	private String assuranceRemark;
	
	/**
	 * 融资租赁
	 */
	private int financeLease;
	
	/**
	 * 融资租赁备注
	 */
	private String financeLeaseRemark;
	
	/**
	 * 诉讼管辖
	 */
	private String litigation;
	
	/**
	 * 我司货代、指定货代
	 */
	private String freight = "我司货代";
	
	/**
	 * 货代备注
	 */
	private String freightRemark;
	
	/**
	 * 物流备注
	 */
	private String logisticsRemark;
	
	private String state = "草稿";
	
	private List<AgentImportGoods> agentImportGoods = new ArrayList<AgentImportGoods>();
	
	private List<AgentImportSupplement> agentImportSupplement = new ArrayList<AgentImportSupplement>();
	
	@Column(name = "MYSELF_UNIT")
	public String getMyselfUnit() {
		return myselfUnit;
	}

	public void setMyselfUnit(String myselfUnit) {
		this.myselfUnit = myselfUnit;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "FOREIGN_ID")
	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "GOODS_TYPE")
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "GOODS_TYPE_REMARK")
	public String getGoodsTypeRemark() {
		return goodsTypeRemark;
	}

	public void setGoodsTypeRemark(String goodsTypeRemark) {
		this.goodsTypeRemark = goodsTypeRemark;
	}

	@Column
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "EXCHANGE_RATE_SELF")
	public Double getExchangeRateSelf() {
		return exchangeRateSelf;
	}

	public void setExchangeRateSelf(Double exchangeRateSelf) {
		this.exchangeRateSelf = exchangeRateSelf;
	}

	@Column(name = "ORIGINAL_CURRENCY")
	public Double getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Double originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column(name = "EXCHANGE_RATE_US")
	public Double getExchangeRateUS() {
		return exchangeRateUS;
	}

	public void setExchangeRateUS(Double exchangeRateUS) {
		this.exchangeRateUS = exchangeRateUS;
	}

	@Column
	public Double getDollar() {
		return dollar;
	}

	public void setDollar(Double dollar) {
		this.dollar = dollar;
	}

	@Column
	public Double getRmb() {
		return rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
	}

	@Column(name = "CUSTOMER_TYPE")
	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "REGISTER_DATE")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "REGISTER_CAPITAL")
	public Double getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(Double registerCapital) {
		this.registerCapital = registerCapital;
	}

	@Column(name = "LINE_CREDIT")
	public Double getLineCredit() {
		return lineCredit;
	}

	public void setLineCredit(Double lineCredit) {
		this.lineCredit = lineCredit;
	}

	@Column(name = "CURRENT_OPEN")
	public Double getCurrentOpen() {
		return currentOpen;
	}

	public void setCurrentOpen(Double currentOpen) {
		this.currentOpen = currentOpen;
	}

	@Column(name = "HAS_BEYOND")
	public Double getHasBeyond() {
		return hasBeyond;
	}

	public void setHasBeyond(Double hasBeyond) {
		this.hasBeyond = hasBeyond;
	}

	@Column(name = "FOREIGN_CONTRACT_NAME")
	public String getForeignContractName() {
		return foreignContractName;
	}

	public void setForeignContractName(String foreignContractName) {
		this.foreignContractName = foreignContractName;
	}

	
	@Column(name = "FOREIGN_CONTRACT_NO")
	public String getForeignContractNo() {
		return foreignContractNo;
	}

	public void setForeignContractNo(String foreignContractNo) {
		this.foreignContractNo = foreignContractNo;
	}

	@Column(name = "FOREIGN_VERSION_SOURCE")
	public int getForeignVersionSource() {
		return foreignVersionSource;
	}

	public void setForeignVersionSource(int foreignVersionSource) {
		this.foreignVersionSource = foreignVersionSource;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<AgentImportGoods> getAgentImportGoods() {
		return agentImportGoods;
	}

	public void setAgentImportGoods(List<AgentImportGoods> agentImportGoods) {
		this.agentImportGoods = agentImportGoods;
	}

	@Column(name = "PAYMENT_METHOD")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "PAYMENT_METHOD_REMARK")
	public String getPaymentMethodRemark() {
		return paymentMethodRemark;
	}

	public void setPaymentMethodRemark(String paymentMethodRemark) {
		this.paymentMethodRemark = paymentMethodRemark;
	}

	@Column(name = "PRICE_TERMS")
	public String getPriceTerms() {
		return priceTerms;
	}

	public void setPriceTerms(String priceTerms) {
		this.priceTerms = priceTerms;
	}

	@Column(name = "SHIPMENT_DATE")
	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	@Column
	public String getDispute() {
		return dispute;
	}

	public void setDispute(String dispute) {
		this.dispute = dispute;
	}

	@Column(name = "INLAND_CONTRACT_NAME")
	public String getInlandContractName() {
		return inlandContractName;
	}

	public void setInlandContractName(String inlandContractName) {
		this.inlandContractName = inlandContractName;
	}

	@Column(name = "INLAND_VERSION_SOURCE")
	public int getInlandVersionSource() {
		return inlandVersionSource;
	}

	public void setInlandVersionSource(int inlandVersionSource) {
		this.inlandVersionSource = inlandVersionSource;
	}

	@Column(name = "AGENCY_FEE")
	public String getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(String agencyFee) {
		this.agencyFee = agencyFee;
	}

	@Column
	public String getAgencyFeeRemark() {
		return agencyFeeRemark;
	}

	public void setAgencyFeeRemark(String agencyFeeRemark) {
		this.agencyFeeRemark = agencyFeeRemark;
	}

	@Column
	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	@Column(name = "PROJECT_CLAIM")
	public String getProjectClaim() {
		return projectClaim;
	}

	public void setProjectClaim(String projectClaim) {
		this.projectClaim = projectClaim;
	}

	@Column(name = "PROJECT_CLAIM_REMARK")
	public String getProjectClaimRemark() {
		return projectClaimRemark;
	}

	public void setProjectClaimRemark(String projectClaimRemark) {
		this.projectClaimRemark = projectClaimRemark;
	}

	@Column
	public int getAssurance() {
		return assurance;
	}

	public void setAssurance(int assurance) {
		this.assurance = assurance;
	}

	@Column(name = "ASSURANCE_REMARK")
	public String getAssuranceRemark() {
		return assuranceRemark;
	}

	public void setAssuranceRemark(String assuranceRemark) {
		this.assuranceRemark = assuranceRemark;
	}

	@Column(name = "FINANCE_LEASE")
	public int getFinanceLease() {
		return financeLease;
	}

	public void setFinanceLease(int financeLease) {
		this.financeLease = financeLease;
	}

	@Column(name = "FINANCE_LEASE_REMARK")
	public String getFinanceLeaseRemark() {
		return financeLeaseRemark;
	}

	public void setFinanceLeaseRemark(String financeLeaseRemark) {
		this.financeLeaseRemark = financeLeaseRemark;
	}

	@Column
	public String getLitigation() {
		return litigation;
	}

	public void setLitigation(String litigation) {
		this.litigation = litigation;
	}

	@Column
	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	@Column
	public String getFreightRemark() {
		return freightRemark;
	}

	public void setFreightRemark(String freightRemark) {
		this.freightRemark = freightRemark;
	}

	@Column
	public String getLogisticsRemark() {
		return logisticsRemark;
	}

	public void setLogisticsRemark(String logisticsRemark) {
		this.logisticsRemark = logisticsRemark;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<AgentImportSupplement> getAgentImportSupplement() {
		Collections.sort(agentImportSupplement, new Comparator<AgentImportSupplement>(){

			@Override
			public int compare(AgentImportSupplement o1, AgentImportSupplement o2) {
				return o2.getCreateDate().compareTo(o1.getCreateDate());
			}
			
		});
		return agentImportSupplement;
	}

	public void setAgentImportSupplement(List<AgentImportSupplement> agentImportSupplement) {
		this.agentImportSupplement = agentImportSupplement;
	}
	
}
