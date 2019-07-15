package com.cbmie.genMac.domesticTrade.entity;

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
 * 采购合同
 * @author wangxiaozheng
 */
@Entity
@Table(name = "domestic_purchase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DomesticPurchase extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
	 * 供应商名称
	 */
	private Long supplier;
	
	/**
	 * 支付方式
	 */
	private String paymentMethod;
	
	/**
	 * 支付方式备注
	 */
	private String paymentMethodRemark;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String state = "草稿";
	
	private List<DomesticPurchaseGoods> domesticPurchaseGoods = new ArrayList<DomesticPurchaseGoods>();
	
	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public Long getSupplier() {
		return supplier;
	}

	public void setSupplier(Long supplier) {
		this.supplier = supplier;
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
	public List<DomesticPurchaseGoods> getDomesticPurchaseGoods() {
		return domesticPurchaseGoods;
	}

	public void setDomesticPurchaseGoods(List<DomesticPurchaseGoods> domesticPurchaseGoods) {
		this.domesticPurchaseGoods = domesticPurchaseGoods;
	}
}
