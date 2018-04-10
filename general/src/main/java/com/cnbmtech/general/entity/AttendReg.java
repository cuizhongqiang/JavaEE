package com.cnbmtech.general.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cnbmtech.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 参加登记
 * @author czq
 */
@Entity
@Table(name = "attend_reg")
@DynamicUpdate @DynamicInsert
public class AttendReg extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6935187372443380724L;

	/**
	 * 类别
	 */
	@NotNull
	@Column
	private String type;
	
	/**
	 * 公司名称
	 */
	@NotNull
	@Column
	private String company;
	
	/**
	 * 姓名
	 */
	@NotNull
	@Column
	private String realName;
	
	/**
	 * 手机
	 */
	@NotNull
	@Column
	private String phone;
	
	/**
	 * 电子邮箱
	 */
	@Column
	private String email;
	
	/**
	 * 职位
	 */
	@Column
	private String position;
	
	/**
	 * 注册ip
	 */
	@Column
	private String regIp;
	
	/**
	 * 注册渠道
	 */
	@Column
	private String channel;
	
	/**
	 * 备用name
	 */
	@Transient
	private String[] remarkName;
	
	/**
	 * 备用type
	 */
	@Transient
	private String[] remarkType;
	
	/**
	 * 备用value
	 */
	@Transient
	private String[] remarkValue;
	
	/**
	 * 备用dictCode
	 */
	@Transient
	private String[] remarkDictCode;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	private List<AttendRegChild> children = new ArrayList<AttendRegChild>();
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String[] getRemarkName() {
		return remarkName == null ? new String[0] : remarkName;
	}

	public void setRemarkName(String[] remarkName) {
		this.remarkName = remarkName;
	}

	public String[] getRemarkType() {
		return remarkType == null ? new String[0] : remarkType;
	}

	public void setRemarkType(String[] remarkType) {
		this.remarkType = remarkType;
	}

	public String[] getRemarkValue() {
		return remarkValue == null ? new String[0] : remarkValue;
	}

	public void setRemarkValue(String[] remarkValue) {
		this.remarkValue = remarkValue;
	}

	public String[] getRemarkDictCode() {
		return remarkDictCode;
	}

	public void setRemarkDictCode(String[] remarkDictCode) {
		this.remarkDictCode = remarkDictCode;
	}

	public List<AttendRegChild> getChildren() {
		return children;
	}

	public void setChildren(List<AttendRegChild> children) {
		this.children = children;
	}

}
