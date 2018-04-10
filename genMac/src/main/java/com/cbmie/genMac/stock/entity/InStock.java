package com.cbmie.genMac.stock.entity;

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
 * 入库
 */
@Entity
@Table(name = "in_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 入库编号
	 */
	private String inStockId;

	/**
	 * 货权单位
	 */
	private String goodsAffiliates;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 电话
	 */
	private String phoneNo;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 提单号
	 */
	private String invoiceNo;

	/**
	 * 存储单位
	 */
	private String storageUnit;

	/**
	 * 仓储地点
	 */
	private String storageLocation;

	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date inStockDate;

	/**
	 * 联系方式
	 */
	private String contactInformation;

	/**
	 * 存储库位
	 */
	private String storagePosition;

	/**
	 * 损耗标准
	 */
	private String lossStandard;

	/**
	 * 仓储期限
	 */
	private String termStorage;

	/**
	 * 仓储费用
	 */
	private String storageFee;

	/**
	 * 起算日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date startDate;

	/**
	 * 保险约定
	 */
	private String insuranceAgreement;

	/**
	 * 设备编号
	 */
	private String deviceID;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 验收记录
	 */
	private String record;

	/**
	 * 仓库填发人
	 */
	private String warehousePrincipal;

	/**
	 * 填发日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date signedDate;
	
	private List<InStockGoods> inStockGoods = new ArrayList<InStockGoods>();

	@Column(name = "IN_STOCK_ID")
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	@Column(name = "GOODS_AFFILATES")
	public String getGoodsAffiliates() {
		return goodsAffiliates;
	}

	public void setGoodsAffiliates(String goodsAffiliates) {
		this.goodsAffiliates = goodsAffiliates;
	}

	@Column(name = "CONTACTS")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	
	@Column(name = "INVOICE_NO")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "STORAGE_UNIT")
	public String getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}

	@Column(name = "STROAGE_LOCATION")
	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	@Column(name = "IN_STOCK_DATE")
	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	@Column(name = "CONTRACT_INFORMATION")
	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	@Column(name = "STORAGE_POSITION")
	public String getStoragePosition() {
		return storagePosition;
	}

	public void setStoragePosition(String storagePosition) {
		this.storagePosition = storagePosition;
	}

	@Column(name = "LOSS_STANDARD")
	public String getLossStandard() {
		return lossStandard;
	}

	public void setLossStandard(String lossStandard) {
		this.lossStandard = lossStandard;
	}

	@Column(name = "TERM_STORAGE")
	public String getTermStorage() {
		return termStorage;
	}

	public void setTermStorage(String termStorage) {
		this.termStorage = termStorage;
	}
	@Column(name = "STORAGE_FEE")
	public String getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(String storageFee) {
		this.storageFee = storageFee;
	}

	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "INSURANCE_AGREEMENT")
	public String getInsuranceAgreement() {
		return insuranceAgreement;
	}

	public void setInsuranceAgreement(String insuranceAgreement) {
		this.insuranceAgreement = insuranceAgreement;
	}

	@Column(name = "DEVICE_ID")
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Column(name = "WAREHOUSE_PRINCIPAL")
	public String getWarehousePrincipal() {
		return warehousePrincipal;
	}

	public void setWarehousePrincipal(String warehousePrincipal) {
		this.warehousePrincipal = warehousePrincipal;
	}

	@Column(name = "SIGNED_DATE")
	public Date getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<InStockGoods> getInStockGoods() {
		return inStockGoods;
	}

	public void setInStockGoods(List<InStockGoods> inStockGoods) {
		this.inStockGoods = inStockGoods;
	}

	
}