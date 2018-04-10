package com.cbmie.genMac.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 仓库
 */
@Entity
@Table(name = "warehouse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Warehouse extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 仓库编码
	 */
	private String warehouseCode;

	/**
	 * 仓库名称
	 */
	private String warehouseName;

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

	private String isRealWarehouse;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 邮编
	 */
	private String zipCode;
	
	/**
	 * 仓储企业
	 */
	private String enterpriseStock;
	
	@Column(name = "enterpris_stock")
	public String getEnterpriseStock() {
		return enterpriseStock;
	}

	public void setEnterpriseStock(String enterpriseStock) {
		this.enterpriseStock = enterpriseStock;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "IS_REAL_WAREHOUSE", nullable = false)
	public String getIsRealWarehouse() {
		return isRealWarehouse;
	}

	public void setIsRealWarehouse(String isRealWarehouse) {
		this.isRealWarehouse = isRealWarehouse;
	}

	/**
	 * 状态：0正常、1停用
	 */
	private String status;

	@Column(name = "WAREHOUSE_CODE", nullable = false)
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name = "WAREHOUSE_NAME", nullable = false)
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column(name = "CONTACT_PERSON", nullable = false)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "PHONE_CONTACT", nullable = false)
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

	@Column(name = "STATUS", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}