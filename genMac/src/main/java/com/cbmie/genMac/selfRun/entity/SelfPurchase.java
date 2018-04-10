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
 * 采购合同（自营）
 * @author czq
 */
@Entity
@Table(name = "self_purchase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SelfPurchase extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6762479300963661009L;

	/**
	 * 我司单位
	 */
	private String myselfUnit;
	
	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
	 * 供应商
	 */
	private Long customerId;
	
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
	 * 支付方式
	 */
	private String paymentMethod;
	
	/**
	 * 支付方式备注
	 */
	private String paymentMethodRemark;
	
	/**
	 * 合同管辖地
	 */
	private String government;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String state = "草稿";
	
	private List<SelfPurchaseGoods> selfPurchaseGoods = new ArrayList<SelfPurchaseGoods>();
	
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<SelfPurchaseGoods> getSelfPurchaseGoods() {
		return selfPurchaseGoods;
	}

	public void setSelfPurchaseGoods(List<SelfPurchaseGoods> selfPurchaseGoods) {
		this.selfPurchaseGoods = selfPurchaseGoods;
	}

}
