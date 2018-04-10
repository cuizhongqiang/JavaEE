package com.cbmie.genMac.credit.entity;

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
 * 开证历史
 * @author czq
 */
@Entity
@Table(name = "open_credit_hi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OpenCreditHistory extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6827399147840915419L;
	

	/**
	 * 外合同号
	 */
	private String foreignContractNo;
	
	/**
	 * 内合同号
	 */
	private String contractNo;
	
	/**
	 * 供应外商
	 */
	private Long foreignId;
	
	/**
	 * 国内客户
	 */
	private Long customerId;
	
	/**
	 * 原币种
	 */
	private String currency;
	
	/**
	 * 合同原币金额
	 */
	private Double originalCurrency;
	
	/**
	 * 折美元
	 */
	private Double dollar;
	
	/**
	 * 折人民币
	 */
	private Double rmb;
	
	/**
	 * 价格条款
	 */
	private String priceTerms;
	
	/**
	 * 信用证类型
	 */
	private int lcType;
	
	/**
	 * 合同类型(0-代理进口,1-自营采购,2-内贸采购,3-内贸代理采购)
	 */
	private int contractType;
	
	/**
	 * 远期天数
	 */
	private Integer days;
	
	/**
	 * 本次应收
	 */
	private Double receivable;
	
	/**
	 * 本次实收
	 */
	private Double receipts;
	
	/**
	 * 金额占合同比例
	 */
	private Double percent;
	
	/**
	 * 开证行
	 */
	private String bank;
	
	/**
	 * 本次金额
	 */
	private Double theMoney;
	
	/**
	 * 开证日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date openDate;
	
	/**
	 * 信用证号
	 */
	private String lcNo;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private List<OpenCreditHistoryGoods> openCreditHistoryGoods = new ArrayList<OpenCreditHistoryGoods>();
	
	/**
	 * 改的开证id
	 */
	private Long changeId;

	@Column(name = "FOREIGN_CONTRACT_NO")
	public String getForeignContractNo() {
		return foreignContractNo;
	}

	public void setForeignContractNo(String foreignContractNo) {
		this.foreignContractNo = foreignContractNo;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "FOREIGN_ID")
	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	@Column(name = "PRICE_TERMS")
	public String getPriceTerms() {
		return priceTerms;
	}

	public void setPriceTerms(String priceTerms) {
		this.priceTerms = priceTerms;
	}

	@Column(name = "LC_TYPE")
	public int getLcType() {
		return lcType;
	}

	public void setLcType(int lcType) {
		this.lcType = lcType;
	}

	@Column
	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	@Column
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Column
	public Double getReceivable() {
		return receivable;
	}

	public void setReceivable(Double receivable) {
		this.receivable = receivable;
	}

	@Column
	public Double getReceipts() {
		return receipts;
	}

	public void setReceipts(Double receipts) {
		this.receipts = receipts;
	}

	
	@Column
	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "THE_MONEY")
	public Double getTheMoney() {
		return theMoney;
	}

	public void setTheMoney(Double theMoney) {
		this.theMoney = theMoney;
	}

	@Column(name = "OPEN_DATE")
	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Column(name = "LC_NO")
	public String getLcNo() {
		return lcNo;
	}

	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<OpenCreditHistoryGoods> getOpenCreditHistoryGoods() {
		return openCreditHistoryGoods;
	}

	public void setOpenCreditHistoryGoods(List<OpenCreditHistoryGoods> openCreditHistoryGoods) {
		this.openCreditHistoryGoods = openCreditHistoryGoods;
	}

	@Column(name = "CHANGE_ID")
	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}
}
