package com.cbmie.genMac.stock.entity;

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
 * 仓储企业评审
 */
@Entity
@Table(name = "enterprise_warehouse_check")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class EnterpriseStockCheck extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 企业名称
	 */
	private String checkId;

	@Column(name = "CHECK_ID")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	/**
	 * 企业名称
	 */
	private String enterpriseName;

	/**
	 * 仓库地址
	 */
	private String warehouseAddress;

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
	 * 企业性质
	 */
	private String enterpriseProperty;

	/**
	 * 资金币种
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
	 * 仓库所有权类型
	 */
	private String warehouseOw;

	/**
	 * 租赁年限
	 */
	private String leasePeriod;

	/**
	 * 已使用年限
	 */
	private String passPeriod;

	/**
	 * 仓库建筑时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date buildTime;

	/**
	 * 是否经营仓储品
	 */
	private String isSealGoods;

	/**
	 * 经营规模
	 */
	private String sealScale;

	/**
	 * 仓储基本情况-总资产
	 */
	private String warehouseTotal;

	/**
	 * 仓储基本情况-面积
	 */
	private String warehouseArea;

	/**
	 * 仓储基本情况-主要仓储品种
	 */
	private String warehouseInstockCategory;

	/**
	 * 仓储基本情况-其他
	 */
	private String warehouseBaseInfo;

	/**
	 * 可提供的担保
	 */
	private String guarantee;

	@Column(name = "GUARANTEE")
	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	/**
	 * 设施设备-其他
	 */
	private String majorEquipment;

	/**
	 * 设施设备-软件
	 */
	private String majorEquipmentSoftware;

	@Column(name = "MAJOR_EQUIPMENT_SOFTWARE")
	public String getMajorEquipmentSoftware() {
		return majorEquipmentSoftware;
	}

	public void setMajorEquipmentSoftware(String majorEquipmentSoftware) {
		this.majorEquipmentSoftware = majorEquipmentSoftware;
	}

	/**
	 * 设施设备-硬件
	 */
	private String majorEquipmentHardware;

	@Column(name = "MAJOR_EQUIPMENT_HARDWARE")
	public String getMajorEquipmentHardware() {
		return majorEquipmentHardware;
	}

	public void setMajorEquipmentHardware(String majorEquipmentHardware) {
		this.majorEquipmentHardware = majorEquipmentHardware;
	}

	/**
	 * 可用资源
	 */
	private String warehouseResources;
	/**
	 * 可用资源-码头
	 */
	private String port;

	@Column(name = "PORT")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * 可用资源-铁路
	 */
	private String railway;

	@Column(name = "RAILWAY")
	public String getRailway() {
		return railway;
	}

	public void setRailway(String railway) {
		this.railway = railway;
	}

	/**
	 * 可用资源-室内厂房
	 */
	private String indoorPlants;

	@Column(name = "INDOORPLANTS")
	public String getIndoorPlants() {
		return indoorPlants;
	}

	public void setIndoorPlants(String indoorPlants) {
		this.indoorPlants = indoorPlants;
	}

	/**
	 * 雇佣人数-仓管员
	 */
	private String warehouseWorkerNumber;

	/**
	 * 雇佣人数-装卸工
	 */
	private String dockerNumber;

	/**
	 * 雇佣人数-管理员
	 */
	private String adminNumber;

	/**
	 * 上年度周转货物总量
	 */
	private String lastYearQuantity;

	/**
	 * 近三个月出库平均量
	 */
	private String recent3MouthPerDayQuantity;

	/**
	 * 现有库存
	 */
	private String existingStocks;

	/**
	 * 最大库存
	 */
	private String maxStocks;

	/**
	 * 仓储企业主要合作客户
	 */
	private String mainPartner;

	/**
	 * 盈利点
	 */
	private String profitPoint;

	/**
	 * 是否有质押仓单业务
	 */
	private String isMortgageBusiness;

	/**
	 * 合作银行
	 */
	private String cooperationBank;

	@Column(name = "COOPERATION_BANK")
	public String getCooperationBank() {
		return cooperationBank;
	}

	public void setCooperationBank(String cooperationBank) {
		this.cooperationBank = cooperationBank;
	}

	/**
	 * 土地抵押情况以及资金用途
	 */
	private String islandMortgage;

	/**
	 * 土地抵押情况以及资金用途
	 */
	private String landMortgageUse;

	/**
	 * 银行其他贷款
	 */
	private String mortgageBank;

	/**
	 * 仓储经营稳定性
	 */
	private String businesStability;

	/**
	 * 是否领导层变动
	 */
	private String isLeaderChange;

	/**
	 * 近两年经营情况
	 */
	private String businessConditions;

	/**
	 * 上班时间
	 */
	private String starWorkTime;

	/**
	 * 下班时间
	 */
	private String endWorkTime;

	/**
	 * 最晚提货时间
	 */
	private String latestDeliveryTime;

	/**
	 * 周六日是否可以提货
	 */
	private String isWeekendDelivery;

	/**
	 * 仓储费用
	 */
	private String warehouseFee;

	/**
	 * 出库费用
	 */
	private String outStockFee;

	/**
	 * 其他费用
	 */
	private String extraFee;

	/**
	 * 仓库是否客户推荐
	 */
	private String isRecommend;

	/**
	 * 准备合作客户
	 */
	private String prepareCooperationCustomer;

	/**
	 * 客户与仓储企业的关系
	 */
	private String customer2Enterprise;

	/**
	 * 客户在库现有库存
	 */
	private String isHaveInstock;

	@Column(name = "IS_HAVE_INSTOCK")
	public String getIsHaveInstock() {
		return isHaveInstock;
	}

	public void setIsHaveInstock(String isHaveInstock) {
		this.isHaveInstock = isHaveInstock;
	}

	/**
	 * 客户在库现有库存
	 */
	private String haveInstock;

	/**
	 * 业务描述
	 */
	private String businessDescription;

	@Column(name = "BUSINESS_DESCRIPTION")
	public String getBusinessDescription() {
		return businessDescription;
	}

	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}

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

	@Column(name = "ENTERPRISE_NAME")
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	@Column(name = "WAREHOUSE_ADDRESS")
	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

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

	@Column(name = "ENTERPRISE_PROPERTY")
	public String getEnterpriseProperty() {
		return enterpriseProperty;
	}

	public void setEnterpriseProperty(String enterpriseProperty) {
		this.enterpriseProperty = enterpriseProperty;
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

	@Column(name = "WAREHOUSE_OW")
	public String getWarehouseOw() {
		return warehouseOw;
	}

	public void setWarehouseOw(String warehouseOw) {
		this.warehouseOw = warehouseOw;
	}

	@Column(name = "LEASE_PERIOD")
	public String getLeasePeriod() {
		return leasePeriod;
	}

	public void setLeasePeriod(String leasePeriod) {
		this.leasePeriod = leasePeriod;
	}

	@Column(name = "PASS_PERIOD")
	public String getPassPeriod() {
		return passPeriod;
	}

	public void setPassPeriod(String passPeriod) {
		this.passPeriod = passPeriod;
	}

	@Column(name = "BUILD_TIME")
	public Date getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	@Column(name = "IS_SEAL_GOODS")
	public String getIsSealGoods() {
		return isSealGoods;
	}

	public void setIsSealGoods(String isSealGoods) {
		this.isSealGoods = isSealGoods;
	}

	@Column(name = "SEAL_SCALE")
	public String getSealScale() {
		return sealScale;
	}

	public void setSealScale(String sealScale) {
		this.sealScale = sealScale;
	}

	@Column(name = "WAREHOUSE_TOTAL")
	public String getWarehouseTotal() {
		return warehouseTotal;
	}

	public void setWarehouseTotal(String warehouseTotal) {
		this.warehouseTotal = warehouseTotal;
	}

	@Column(name = "WAREHOUSE_AREA")
	public String getWarehouseArea() {
		return warehouseArea;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}

	@Column(name = "WAREHOUSE_INSTOCK_CATEGORY")
	public String getWarehouseInstockCategory() {
		return warehouseInstockCategory;
	}

	public void setWarehouseInstockCategory(String warehouseInstockCategory) {
		this.warehouseInstockCategory = warehouseInstockCategory;
	}

	@Column(name = "WAREHOUSE_BASE_INFO")
	public String getWarehouseBaseInfo() {
		return warehouseBaseInfo;
	}

	public void setWarehouseBaseInfo(String warehouseBaseInfo) {
		this.warehouseBaseInfo = warehouseBaseInfo;
	}

	@Column(name = "MAJOR_EQUIPMENT")
	public String getMajorEquipment() {
		return majorEquipment;
	}

	public void setMajorEquipment(String majorEquipment) {
		this.majorEquipment = majorEquipment;
	}

	@Column(name = "WAREHOUSE_RESOURCES")
	public String getWarehouseResources() {
		return warehouseResources;
	}

	public void setWarehouseResources(String warehouseResources) {
		this.warehouseResources = warehouseResources;
	}

	@Column(name = "WAREHOUSE_WORKER_NUMBER")
	public String getWarehouseWorkerNumber() {
		return warehouseWorkerNumber;
	}

	public void setWarehouseWorkerNumber(String warehouseWorkerNumber) {
		this.warehouseWorkerNumber = warehouseWorkerNumber;
	}

	@Column(name = "DOCKER_NUMBER")
	public String getDockerNumber() {
		return dockerNumber;
	}

	public void setDockerNumber(String dockerNumber) {
		this.dockerNumber = dockerNumber;
	}

	@Column(name = "ADMIN_NUMBER")
	public String getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(String adminNumber) {
		this.adminNumber = adminNumber;
	}

	@Column(name = "LAST_YEAR_QUANTITY")
	public String getLastYearQuantity() {
		return lastYearQuantity;
	}

	public void setLastYearQuantity(String lastYearQuantity) {
		this.lastYearQuantity = lastYearQuantity;
	}

	@Column(name = "RECENT_3MOUTH_PERDAY_QUANTITY")
	public String getRecent3MouthPerDayQuantity() {
		return recent3MouthPerDayQuantity;
	}

	public void setRecent3MouthPerDayQuantity(String recent3MouthPerDayQuantity) {
		this.recent3MouthPerDayQuantity = recent3MouthPerDayQuantity;
	}

	@Column(name = "EXISTING_STOCKS")
	public String getExistingStocks() {
		return existingStocks;
	}

	public void setExistingStocks(String existingStocks) {
		this.existingStocks = existingStocks;
	}

	@Column(name = "MAX_STOCKS")
	public String getMaxStocks() {
		return maxStocks;
	}

	public void setMaxStocks(String maxStocks) {
		this.maxStocks = maxStocks;
	}

	@Column(name = "MAIN_PARTNER")
	public String getMainPartner() {
		return mainPartner;
	}

	public void setMainPartner(String mainPartner) {
		this.mainPartner = mainPartner;
	}

	@Column(name = "PROFIT_POINT")
	public String getProfitPoint() {
		return profitPoint;
	}

	public void setProfitPoint(String profitPoint) {
		this.profitPoint = profitPoint;
	}

	@Column(name = "IS_MORTGAGE_BUSINESS")
	public String getIsMortgageBusiness() {
		return isMortgageBusiness;
	}

	public void setIsMortgageBusiness(String isMortgageBusiness) {
		this.isMortgageBusiness = isMortgageBusiness;
	}

	@Column(name = "IS_LAND_MORTGAGE")
	public String getIslandMortgage() {
		return islandMortgage;
	}

	public void setIslandMortgage(String islandMortgage) {
		this.islandMortgage = islandMortgage;
	}

	@Column(name = "LAND_MORTGAGE_USE")
	public String getLandMortgageUse() {
		return landMortgageUse;
	}

	public void setLandMortgageUse(String landMortgageUse) {
		this.landMortgageUse = landMortgageUse;
	}

	@Column(name = "MORTGAGE_BANK")
	public String getMortgageBank() {
		return mortgageBank;
	}

	public void setMortgageBank(String mortgageBank) {
		this.mortgageBank = mortgageBank;
	}

	@Column(name = "BUSINES_STABILITY")
	public String getBusinesStability() {
		return businesStability;
	}

	public void setBusinesStability(String businesStability) {
		this.businesStability = businesStability;
	}

	@Column(name = "IS_LEADER_CHANGE")
	public String getIsLeaderChange() {
		return isLeaderChange;
	}

	public void setIsLeaderChange(String isLeaderChange) {
		this.isLeaderChange = isLeaderChange;
	}

	@Column(name = "BUSINESS_CONDITIONS")
	public String getBusinessConditions() {
		return businessConditions;
	}

	public void setBusinessConditions(String businessConditions) {
		this.businessConditions = businessConditions;
	}

	@Column(name = "STAR_WORK_TIME")
	public String getStarWorkTime() {
		return starWorkTime;
	}

	public void setStarWorkTime(String starWorkTime) {
		this.starWorkTime = starWorkTime;
	}

	@Column(name = "END_WORK_TIME")
	public String getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

	@Column(name = "LATEST_DELIVERY_TIME")
	public String getLatestDeliveryTime() {
		return latestDeliveryTime;
	}

	public void setLatestDeliveryTime(String latestDeliveryTime) {
		this.latestDeliveryTime = latestDeliveryTime;
	}

	@Column(name = "IS_WEEKEND_DELIVERY")
	public String getIsWeekendDelivery() {
		return isWeekendDelivery;
	}

	public void setIsWeekendDelivery(String isWeekendDelivery) {
		this.isWeekendDelivery = isWeekendDelivery;
	}

	@Column(name = "WAREHOUSE_FEE")
	public String getWarehouseFee() {
		return warehouseFee;
	}

	public void setWarehouseFee(String warehouseFee) {
		this.warehouseFee = warehouseFee;
	}

	@Column(name = "OUT_STOCK_FEE")
	public String getOutStockFee() {
		return outStockFee;
	}

	public void setOutStockFee(String outStockFee) {
		this.outStockFee = outStockFee;
	}

	@Column(name = "EXTRA_FEE")
	public String getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(String extraFee) {
		this.extraFee = extraFee;
	}

	@Column(name = "IS_RECOMMEND")
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Column(name = "PERPARE_COOPERATION_CUSTOMER")
	public String getPrepareCooperationCustomer() {
		return prepareCooperationCustomer;
	}

	public void setPrepareCooperationCustomer(String prepareCooperationCustomer) {
		this.prepareCooperationCustomer = prepareCooperationCustomer;
	}

	@Column(name = "CUSTOMER2_ENTERPRISE")
	public String getCustomer2Enterprise() {
		return customer2Enterprise;
	}

	public void setCustomer2Enterprise(String customer2Enterprise) {
		this.customer2Enterprise = customer2Enterprise;
	}

	@Column(name = "HAVE_INSTOCK")
	public String getHaveInstock() {
		return haveInstock;
	}

	public void setHaveInstock(String haveInstock) {
		this.haveInstock = haveInstock;
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

}