package com.cnbmtech.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.cnbmtech.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sys_user")
@DynamicUpdate @DynamicInsert
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8177880538351815212L;

	/**
	 * 用户名
	 */
	@Column
	private String userName;
	
	/**
	 * 不持久化到数据库，也不显示在Restful接口的属性.
	 */
	@Transient
	@JsonIgnore
	private String plainPassword;

	/**
	 * 密码
	 */
	@Column
	private String password;
	
	/**
	 * 秘钥
	 */
	@Column
	private String salt;
	
	/**
	 * 真实姓名
	 */
	@Column
	private String realName;
	
	/**
	 * 最后一次登录时间
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastLoginTime;
	
	/**
	 * 最后一次登录ip
	 */
	@Column
	private String lastLoginIP;
	
	/**
	 * 登录次数
	 */
	@Column
	private Integer loginNum;
	
	/**
	 * 性别
	 */
	@Column
	private String gender;
	
	/**
	 * 生日
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	/**
	 * 电话
	 */
	@Column
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column
	private String email;
	
	/**
	 * 地址
	 */
	@Column
	private String address;
	
	/**
	 * 个人信息
	 */
	@Column
	private String userInfo;
	
	/**
	 * 状态
	 * 1-启用，0-禁用
	 */
	@Column
	private int status = 1;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
