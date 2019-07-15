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

/**
 * 进口结算联系单
 */
@Entity
@Table(name = "import_billing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ImportBilling extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 购货单位名称
	 */
	private String unitName;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 税务登记号
	 */
	private String taxNo;
	
	/**
	 * 开户行
	 */
	private String bankName;
	
	/**
	 * 账号
	 */
	private String bankNo;
	
	/**
	 * 进口合同号
	 */
	private String contractNo;
	
	/**
	 * 现有库存
	 */
	private Double nowInventory;
	
	/**
	 * 剩余库存
	 */
	private Double remainInventory;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 业务员分成比例
	 */
	private String proportion;
	
	/**
	 * 结算日期
	 */
	private Date settlementDate;
	
	/**
	 * 支付货款
	 */
	private Double payMoney;
	
	/**
	 * 支付货款
	 */
	private Double payMoneySys;
	
	/**
	 * 到账货款
	 */
	private Double accountMoney;
	
	
	/**
	 * 银行手续费
	 */
	private Double bankPoundage;
	
	/**
	 * 银行手续费
	 */
	private Double bankPoundageSys;
	
	/**
	 * 关税（监管费）
	 */
	private Double custom;
	
	/**
	 * 关税（监管费）
	 */
	private Double customSys;
	
	/**
	 * 增值税
	 */
	private Double vat;
	
	/**
	 * 增值税
	 */
	private Double vatSys;
	
	/**
	 * 消费税
	 */
	private Double saleTax;
	
	/**
	 * 消费税
	 */
	private Double saleTaxSys;
	
	/**
	 * 报关
	 */
	private Double customsDeclaration;
	
	/**
	 * 报关
	 */
	private Double customsDeclarationSys;
	
	/**
	 * 运杂
	 */
	private Double shipMix;
	
	/**
	 * 运杂
	 */
	private Double shipMixSys;
	
	
	/**
	 * 保险
	 */
	private Double insurance;
	
	/**
	 * 保险
	 */
	private Double insuranceSys;
	
	/**
	 * 代理费（利润）
	 */
	private String agencyFee;
	
	/**
	 * 其他（免表、录入等）
	 */
	private Double others;
	
	/**
	 * 其他（免表、录入等）
	 */
	private Double othersSys;
	
	/**
	 * 利息
	 */
	private Double interest;
	
	/**
	 * 支付合计
	 */
	private Double payTotal;
	
	/**
	 * 支付合计
	 */
	private Double payTotalSys;
	
	/**
	 * 本合同余额
	 */
	private Double balance;
	
	/**
	 * 代理客户名称
	 */
	private String agencyName;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private List<ImportBillingGoods> importBillingGoods = new ArrayList<ImportBillingGoods>();
	
	private String state = "草稿";

	@Column(name = "UNIT_NAME")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "TAX_NO")
	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_NO")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "NOW_INVENTORY")
	public Double getNowInventory() {
		return nowInventory;
	}

	public void setNowInventory(Double nowInventory) {
		this.nowInventory = nowInventory;
	}

	@Column(name = "REMAIN_INVENTORY")
	public Double getRemainInventory() {
		return remainInventory;
	}

	public void setRemainInventory(Double remainInventory) {
		this.remainInventory = remainInventory;
	}
	
	@Column(name = "CURRENCY")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	@Column(name = "SETTLEMENT_DATE")
	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Column(name = "PAY_MONEY")
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	@Column(name = "ACCOUNT_MONEY")
	public Double getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(Double accountMoney) {
		this.accountMoney = accountMoney;
	}

	@Column(name = "BANK_POUNDAGE")
	public Double getBankPoundage() {
		return bankPoundage;
	}

	public void setBankPoundage(Double bankPoundage) {
		this.bankPoundage = bankPoundage;
	}

	@Column
	public Double getCustom() {
		return custom;
	}

	public void setCustom(Double custom) {
		this.custom = custom;
	}

	@Column
	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	@Column
	public Double getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(Double saleTax) {
		this.saleTax = saleTax;
	}

	@Column(name = "CUSTOMS_DECLARATION")
	public Double getCustomsDeclaration() {
		return customsDeclaration;
	}

	public void setCustomsDeclaration(Double customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	@Column(name = "SHIP_MIX")
	public Double getShipMix() {
		return shipMix;
	}

	public void setShipMix(Double shipMix) {
		this.shipMix = shipMix;
	}

	@Column
	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	@Column(name = "AGENCY_FEE")
	public String getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(String agencyFee) {
		this.agencyFee = agencyFee;
	}

	@Column
	public Double getOthers() {
		return others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}

	@Column
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	@Column(name = "PAY_TOTAL")
	public Double getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(Double payTotal) {
		this.payTotal = payTotal;
	}

	@Column
	public Double getPayMoneySys() {
		return payMoneySys;
	}

	public void setPayMoneySys(Double payMoneySys) {
		this.payMoneySys = payMoneySys;
	}

	@Column
	public Double getBankPoundageSys() {
		return bankPoundageSys;
	}

	public void setBankPoundageSys(Double bankPoundageSys) {
		this.bankPoundageSys = bankPoundageSys;
	}

	@Column
	public Double getCustomSys() {
		return customSys;
	}

	public void setCustomSys(Double customSys) {
		this.customSys = customSys;
	}

	@Column
	public Double getVatSys() {
		return vatSys;
	}

	public void setVatSys(Double vatSys) {
		this.vatSys = vatSys;
	}

	@Column
	public Double getSaleTaxSys() {
		return saleTaxSys;
	}

	public void setSaleTaxSys(Double saleTaxSys) {
		this.saleTaxSys = saleTaxSys;
	}

	@Column
	public Double getCustomsDeclarationSys() {
		return customsDeclarationSys;
	}

	public void setCustomsDeclarationSys(Double customsDeclarationSys) {
		this.customsDeclarationSys = customsDeclarationSys;
	}

	@Column
	public Double getShipMixSys() {
		return shipMixSys;
	}

	public void setShipMixSys(Double shipMixSys) {
		this.shipMixSys = shipMixSys;
	}

	@Column
	public Double getInsuranceSys() {
		return insuranceSys;
	}

	public void setInsuranceSys(Double insuranceSys) {
		this.insuranceSys = insuranceSys;
	}

	@Column
	public Double getOthersSys() {
		return othersSys;
	}

	public void setOthersSys(Double othersSys) {
		this.othersSys = othersSys;
	}

	@Column
	public Double getPayTotalSys() {
		return payTotalSys;
	}

	public void setPayTotalSys(Double payTotalSys) {
		this.payTotalSys = payTotalSys;
	}

	@Column
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "AGENCY_NAME")
	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<ImportBillingGoods> getImportBillingGoods() {
		return importBillingGoods;
	}

	public void setImportBillingGoods(List<ImportBillingGoods> importBillingGoods) {
		this.importBillingGoods = importBillingGoods;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}