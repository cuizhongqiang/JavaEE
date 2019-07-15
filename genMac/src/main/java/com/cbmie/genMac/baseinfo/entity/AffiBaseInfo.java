package com.cbmie.genMac.baseinfo.entity;

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
 * 关联单位__基本信息
 */
@Entity
@Table(name = "AFFI_BASE_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AffiBaseInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户英文名
	 */
	private String customerEnName;

	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**
	 * 注册时间
	 */
	private Date registerDate;
	
	/**
	 * 注册资本
	 */
	private Double registerCapital;

	/**
	 * 法定代表人姓名
	 */
	private String legalName;

	/**
	 * 身份号码
	 */
	private String idCardNO;

	/**
	 * 业务范围
	 */
	private String businessScope;

	/**
	 * 国别地区
	 */
	private String country;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 网址
	 */
	private String internetSite;

	/**
	 * 联系人
	 */
	private String contactPerson;

	/**
	 * 联系电话
	 */
	private String phoneContact;

	/**
	 * 国内税务编号
	 */
	private String internalTaxNO;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 状态：0正常、1停用
	 */
	private Integer status;
	
	/**
	 * 备注
	 */
	private String comments;
	
	/**
	 * 银行信息
	 */
	private List<AffiBankInfo> affiBankInfo = new ArrayList<AffiBankInfo>();
	
	/**
	 * 客户评审信息
	 */
	private List<AffiCustomerInfo> affiCustomerInfo = new ArrayList<AffiCustomerInfo>();
	
	/**
	 * 担保信息
	 */
	private List<AffiAssureInfo> affiAssureInfo = new ArrayList<AffiAssureInfo>();


	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "CUSTOMER_CODE")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Column(name = "CUSTOMER_NAME", nullable = false)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_EN_NAME")
	public String getCustomerEnName() {
		return customerEnName;
	}

	public void setCustomerEnName(String customerEnName) {
		this.customerEnName = customerEnName;
	}

	@Column(name = "CUSTOMER_TYPE")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Column(name = "REGISTER_DATE")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "REGISTER_CAPITAL")
	public Double getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(Double registerCapital) {
		this.registerCapital = registerCapital;
	}

	@Column(name = "LEGAL_NAME")
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	@Column(name = "ID_CARD_NO")
	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	@Column(name = "BUSINESS_SCOPE")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "INTERNET_SITE")
	public String getInternetSite() {
		return internetSite;
	}

	public void setInternetSite(String internetSite) {
		this.internetSite = internetSite;
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

	@Column(name = "INTERNAL_TAX_NO")
	public String getInternalTaxNO() {
		return internalTaxNO;
	}

	public void setInternalTaxNO(String internalTaxNO) {
		this.internalTaxNO = internalTaxNO;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<AffiBankInfo> getAffiBankInfo() {
		return affiBankInfo;
	}

	public void setAffiBankInfo(List<AffiBankInfo> affiBankInfo) {
		this.affiBankInfo = affiBankInfo;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<AffiCustomerInfo> getAffiCustomerInfo() {
		return affiCustomerInfo;
	}

	public void setAffiCustomerInfo(List<AffiCustomerInfo> affiCustomerInfo) {
		this.affiCustomerInfo = affiCustomerInfo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<AffiAssureInfo> getAffiAssureInfo() {
		return affiAssureInfo;
	}

	public void setAffiAssureInfo(List<AffiAssureInfo> affiAssureInfo) {
		this.affiAssureInfo = affiAssureInfo;
	}
	
}