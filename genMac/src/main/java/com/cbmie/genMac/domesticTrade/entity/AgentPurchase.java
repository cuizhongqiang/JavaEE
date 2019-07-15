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
 * 代理采购合同
 * @author wangxiaozheng
 */
@Entity
@Table(name = "agent_purchase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AgentPurchase extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
	 * 客户名称
	 */
	private Long customerName;
	
	/**
	 * 付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 代理费 
	 */
	private String agencyFee;
	
	/**
	 * 支付方式备注
	 */
	private String paymentMethodRemark;
	
	/**
	 * 担保
	 */
	private String guartee;
		
	/**
	 * 合同管辖地
	 */
	private String adress;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 物流方案
	 */
	private String logScheme;
	
	private String state = "草稿";
	
	private List<AgentPurchaseGoods> agentPurchaseGoods = new ArrayList<AgentPurchaseGoods>();

	@Column(name = "contractNo", nullable = false)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "customerName", nullable = false)
	public Long getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Long customerName) {
		this.customerName = customerName;
	}

	@Column(name = "paymentMethod", nullable = false)
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "agencyFee", nullable = false)
	public String getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(String agencyFee) {
		this.agencyFee = agencyFee;
	}

	@Column(name = "paymentMethodRemark", nullable = false)
	public String getPaymentMethodRemark() {
		return paymentMethodRemark;
	}

	public void setPaymentMethodRemark(String paymentMethodRemark) {
		this.paymentMethodRemark = paymentMethodRemark;
	}

	@Column(name = "guartee", nullable = false)
	public String getGuartee() {
		return guartee;
	}

	public void setGuartee(String guartee) {
		this.guartee = guartee;
	}

	@Column(name = "adress", nullable = false)
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Column(name = "remark", nullable = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "logScheme", nullable = false)
	public String getLogScheme() {
		return logScheme;
	}

	public void setLogScheme(String logScheme) {
		this.logScheme = logScheme;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<AgentPurchaseGoods> getAgentPurchaseGoods() {
		return agentPurchaseGoods;
	}

	public void setAgentPurchaseGoods(List<AgentPurchaseGoods> agentPurchaseGoods) {
		this.agentPurchaseGoods = agentPurchaseGoods;
	}
		
}
