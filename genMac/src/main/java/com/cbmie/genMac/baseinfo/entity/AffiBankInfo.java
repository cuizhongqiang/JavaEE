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
 * 关联单位__银行信息
 */
@Entity
@Table(name = "AFFI_BANK_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AffiBankInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 帐号
	 */
	private String bankNO;

	/**
	 * 省市信息
	 */
	private String partitionInfo;

	/**
	 * 联系人
	 */
	private String contactPerson;

	/**
	 * 联系电话
	 */
	private String phoneContact;
	
	/**
	 * 关联id
	 */
	private String parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "BANK_NAME", nullable = false)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_NO", nullable = false)
	public String getBankNO() {
		return bankNO;
	}

	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}

	@Column(name = "PARTITION_INFO", nullable = false)
	public String getPartitionInfo() {
		return partitionInfo;
	}

	public void setPartitionInfo(String partitionInfo) {
		this.partitionInfo = partitionInfo;
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

}