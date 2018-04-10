package com.cbmie.genMac.selfRun.entity;

import java.util.ArrayList;
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
 * 销售合同（自营）
 * @author czq
 */
@Entity
@Table(name = "self_sales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SelfSales extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3328524832459364123L;

	/**
	 * 我司单位
	 */
	private String myselfUnit;
	
	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
	 * 客户
	 */
	private Long customer;
	
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
	 * 付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 付款方式备注
	 */
	private String paymentMethodRemark;
	
	/**
	 * 保证金
	 */
	private String margin;
	
	/**
	 * 合同管辖地
	 */
	private String government;
	
	/**
	 * 担保措施
	 */
	private int assurance = 0;
	
	/**
	 * 担保措施备注
	 */
	private String assuranceRemark;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 物流方案
	 */
	private String logistics;
	
	private String state = "草稿";
	
	private List<SelfSalesGoods> selfSalesGoods = new ArrayList<SelfSalesGoods>();
	
	@Column
	public String getMyselfUnit() {
		return myselfUnit;
	}

	public void setMyselfUnit(String myselfUnit) {
		this.myselfUnit = myselfUnit;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	@Column
	public Double getExchangeRateSelf() {
		return exchangeRateSelf;
	}

	public void setExchangeRateSelf(Double exchangeRateSelf) {
		this.exchangeRateSelf = exchangeRateSelf;
	}

	@Column
	public Double getExchangeRateUS() {
		return exchangeRateUS;
	}

	public void setExchangeRateUS(Double exchangeRateUS) {
		this.exchangeRateUS = exchangeRateUS;
	}

	@Column
	public Double getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Double originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column
	public Double getRmb() {
		return rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
	}

	@Column
	public Double getDollar() {
		return dollar;
	}

	public void setDollar(Double dollar) {
		this.dollar = dollar;
	}

	@Column
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column
	public String getPaymentMethodRemark() {
		return paymentMethodRemark;
	}

	public void setPaymentMethodRemark(String paymentMethodRemark) {
		this.paymentMethodRemark = paymentMethodRemark;
	}

	@Column
	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	@Column
	public String getGovernment() {
		return government;
	}

	public void setGovernment(String government) {
		this.government = government;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column
	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	@Column
	public int getAssurance() {
		return assurance;
	}

	public void setAssurance(int assurance) {
		this.assurance = assurance;
	}

	@Column
	public String getAssuranceRemark() {
		return assuranceRemark;
	}

	public void setAssuranceRemark(String assuranceRemark) {
		this.assuranceRemark = assuranceRemark;
	}

	@Column
	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<SelfSalesGoods> getSelfSalesGoods() {
		return selfSalesGoods;
	}

	public void setSelfSalesGoods(List<SelfSalesGoods> selfSalesGoods) {
		this.selfSalesGoods = selfSalesGoods;
	}

}
