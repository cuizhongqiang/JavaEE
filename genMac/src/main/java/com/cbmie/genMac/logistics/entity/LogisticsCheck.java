package com.cbmie.genMac.logistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 物流合作公司评审
 */
@Entity
@Table(name = "logistics_check")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class LogisticsCheck extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 评审编号
	 */
	private String checkId;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 联系人
	 */
	private String contactPerson;

	/**
	 * 联系电话
	 */
	private String phoneContact;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 申请部门
	 */
	private String applyDepart;

	/**
	 * 申请人
	 */
	private String applyPerson;

	/**
	 * 申请时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date applyDate;

	/**
	 * 公司性质
	 */
	private String companyProperty;

	/**
	 * 注册资金币种
	 */
	private String capitalCurrency;

	@Column(name = "CAPITAL_CURRENCY")
	public String getCapitalCurrency() {
		return capitalCurrency;
	}

	public void setCapitalCurrency(String capitalCurrency) {
		this.capitalCurrency = capitalCurrency;
	}

	/**
	 * 注册资金
	 */
	private String registeredCapital;

	/**
	 * 营业年限 --成立日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date businessLifeFound;

	@Column(name = "BUSINESS_LIFE_FOUND")
	public Date getBusinessLifeFound() {
		return businessLifeFound;
	}

	public void setBusinessLifeFound(Date businessLifeFound) {
		this.businessLifeFound = businessLifeFound;
	}

	/**
	 * 营业年限 --营业期限
	 */
	private String businessLifeDealline;

	@Column(name = "BUSINESS_LIFE_DEADLINE")
	public String getBusinessLifeDealline() {
		return businessLifeDealline;
	}

	public void setBusinessLifeDealline(String businessLifeDealline) {
		this.businessLifeDealline = businessLifeDealline;
	}

	/**
	 * 营业年限--已营业
	 */
	private String businessLife;

	/**
	 * 法定代表人
	 */
	private String corporate;

	/**
	 * 实际控制人
	 */
	private String actualControlPerson;

	/**
	 * 在岗人数
	 */
	private String inPostPerson;

	/**
	 * 是否有《道路/水路运输资格》
	 */
	private String haveTransportAble;

	/**
	 * 自有车辆多少台
	 */
	private String haveCar;

	/**
	 * 自有船舶多少台
	 */
	private String havaShip;

	/**
	 * 海关监管车
	 */
	private String customsControlVehicles;

	/**
	 * 其他运输方案
	 */
	private String extraTransportProgram;

	/**
	 * 擅长项目、产品、进口出口清关操作
	 */
	private String expertProject;

	/**
	 * 上一营业年度报关票数
	 */
	private String lastVotes;

	/**
	 * 营业收入
	 */
	private String income;

	/**
	 * 海关、商检监管信息：信用信息
	 */
	private String creditInformation;

	/**
	 * 海关、商检监管信息：处罚信息
	 */
	private String punishmentInformation;

	/**
	 * 雇佣人数-总人数
	 */
	private String totalWorker;

	@Column(name = "TOTAL_WORKER")
	public String getTotalWorker() {
		return totalWorker;
	}

	public void setTotalWorker(String totalWorker) {
		this.totalWorker = totalWorker;
	}

	/**
	 * 雇佣人数-操作员
	 */
	private String workerNumber;

	/**
	 * 雇佣人数-管理员
	 */
	private String adminNumber;

	/**
	 * 仓储企业已提供的资料
	 */
	private String enterpriseWarehouseData;

	/**
	 * 合同是否已签订
	 */
	private String isContract;

	/**
	 * 哪方面版本
	 */
	private String contractCategory;

	/**
	 * 是否当面办理
	 */
	private String isFaceToFace;

	/**
	 * 现场考察及班里人意见
	 */
	private String sitePersonnel;

	/**
	 * 状态
	 */
	private String state = "草稿";

	@Column(name = "CONTACT_PERSON")
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "PHONE_CONTACT")
	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "APPLY_DEPART")
	public String getApplyDepart() {
		return applyDepart;
	}

	public void setApplyDepart(String applyDepart) {
		this.applyDepart = applyDepart;
	}

	@Column(name = "APPLY_PERSON")
	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	@Column(name = "APPLY_DATE")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "REGISTERED_CAPITAL")
	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	@Column(name = "BUSINESS_LIFE")
	public String getBusinessLife() {
		return businessLife;
	}

	public void setBusinessLife(String businessLife) {
		this.businessLife = businessLife;
	}

	@Column(name = "ADMIN_NUMBER")
	public String getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(String adminNumber) {
		this.adminNumber = adminNumber;
	}

	@Column(name = "ENTERPRISE_WAREHOUSE_DATA")
	public String getEnterpriseWarehouseData() {
		return enterpriseWarehouseData;
	}

	public void setEnterpriseWarehouseData(String enterpriseWarehouseData) {
		this.enterpriseWarehouseData = enterpriseWarehouseData;
	}

	@Column(name = "IS_CONTARCT")
	public String getIsContract() {
		return isContract;
	}

	public void setIsContract(String isContract) {
		this.isContract = isContract;
	}

	@Column(name = "CONTRACT_CATEGORY")
	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	@Column(name = "IS_FACETOFACE")
	public String getIsFaceToFace() {
		return isFaceToFace;
	}

	public void setIsFaceToFace(String isFaceToFace) {
		this.isFaceToFace = isFaceToFace;
	}

	@Column(name = "SITE_PERSONNEL")
	public String getSitePersonnel() {
		return sitePersonnel;
	}

	public void setSitePersonnel(String sitePersonnel) {
		this.sitePersonnel = sitePersonnel;
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CHECK_ID")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name = "COMPANY_NAME")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "COMPANY_ADRESS")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "COMPANY_PROPERTY")
	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	@Column(name = "CORPORATE")
	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	@Column(name = "ACTUAL_CONTROL_PERSON")
	public String getActualControlPerson() {
		return actualControlPerson;
	}

	public void setActualControlPerson(String actualControlPerson) {
		this.actualControlPerson = actualControlPerson;
	}

	@Column(name = "INPOST_PERSON")
	public String getInPostPerson() {
		return inPostPerson;
	}

	public void setInPostPerson(String inPostPerson) {
		this.inPostPerson = inPostPerson;
	}

	@Column(name = "HAVE_TRANSPORT_ABLE")
	public String getHaveTransportAble() {
		return haveTransportAble;
	}

	public void setHaveTransportAble(String haveTransportAble) {
		this.haveTransportAble = haveTransportAble;
	}

	@Column(name = "HAVE_CAR")
	public String getHaveCar() {
		return haveCar;
	}

	public void setHaveCar(String haveCar) {
		this.haveCar = haveCar;
	}

	@Column(name = "HAVE_SHIP")
	public String getHavaShip() {
		return havaShip;
	}

	public void setHavaShip(String havaShip) {
		this.havaShip = havaShip;
	}

	@Column(name = "CUSTOMS_CONTROL_VEHICLES")
	public String getCustomsControlVehicles() {
		return customsControlVehicles;
	}

	public void setCustomsControlVehicles(String customsControlVehicles) {
		this.customsControlVehicles = customsControlVehicles;
	}

	@Column(name = "EXTRA_TRANSPORT_PROGRAM")
	public String getExtraTransportProgram() {
		return extraTransportProgram;
	}

	public void setExtraTransportProgram(String extraTransportProgram) {
		this.extraTransportProgram = extraTransportProgram;
	}

	@Column(name = "EXPERT_PROJECT")
	public String getExpertProject() {
		return expertProject;
	}

	public void setExpertProject(String expertProject) {
		this.expertProject = expertProject;
	}

	@Column(name = "LAST_VOTES")
	public String getLastVotes() {
		return lastVotes;
	}

	public void setLastVotes(String lastVotes) {
		this.lastVotes = lastVotes;
	}

	@Column(name = "INCOME")
	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	@Column(name = "CREDIT_INFORMATION")
	public String getCreditInformation() {
		return creditInformation;
	}

	public void setCreditInformation(String creditInformation) {
		this.creditInformation = creditInformation;
	}

	@Column(name = "PUNISHMENT_INFORMATION")
	public String getPunishmentInformation() {
		return punishmentInformation;
	}

	public void setPunishmentInformation(String punishmentInformation) {
		this.punishmentInformation = punishmentInformation;
	}

	@Column(name = "WORKER_NUMBER")
	public String getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(String workerNumber) {
		this.workerNumber = workerNumber;
	}

}