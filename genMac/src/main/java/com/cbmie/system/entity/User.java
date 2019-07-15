package com.cbmie.system.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户entity
 */
@Entity
@Table(name = "user")
@DynamicUpdate @DynamicInsert
public class User implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String loginName;
	private String name;
	private String password;
	private String plainPassword;
	private String salt;
	private Timestamp birthday;
	private Short gender;
	private String email;
	private String phone;
	private String icon;
	private Timestamp createDate;
	private String state;
	private String description;
	private Integer loginCount;
	private Timestamp previousVisit;
	private Timestamp lastVisit;
	private String delFlag;
	
	/***add by linxiaopeng  ***/
	//手机登陆权限 （1是：可登录；0否 不可登陆）
	private String phonePermission;
	
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	@JsonIgnore
	private Organization organization;

	// Constructors

	/** default constructor */
	public User() {
	}
	
	public User(Integer id) {
		this.id=id;
	}

	/** minimal constructor */
	public User(String loginName, String name, String password) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
	}

	/** full constructor */
	public User(String loginName, String name, String password, String salt,
			Timestamp birthday, Short gender, String email, String phone,
			String icon, Timestamp createDate, String state,String description,
			Integer loginCount, Timestamp previousVisit, Timestamp lastVisit,
			String delFlag, Set<UserRole> userRoles,String phonePermission) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.salt = salt;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.icon = icon;
		this.createDate = createDate;
		this.state = state;
		this.description=description;
		this.loginCount = loginCount;
		this.previousVisit = previousVisit;
		this.lastVisit = lastVisit;
		this.delFlag = delFlag;
		this.userRoles = userRoles;
		this.phonePermission = phonePermission;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "SALT")
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "BIRTHDAY", length = 19)
	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	@Column(name = "GENDER")
	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ICON", length = 500)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "CREATE_DATE", length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "LOGIN_COUNT")
	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "PREVIOUS_VISIT", length = 19)
	public Timestamp getPreviousVisit() {
		return this.previousVisit;
	}

	public void setPreviousVisit(Timestamp previousVisit) {
		this.previousVisit = previousVisit;
	}

	@Column(name = "LAST_VISIT", length = 19)
	public Timestamp getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(Timestamp lastVisit) {
		this.lastVisit = lastVisit;
	}

	@Column(name = "DEL_FLAG", length = 1)
	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_ID")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	
	@Column(name = "PHONE_PERMISSION", nullable = true)
	public String getPhonePermission() {
		return phonePermission;
	}

	public void setPhonePermission(String phonePermission) {
		this.phonePermission = phonePermission;
	}

}