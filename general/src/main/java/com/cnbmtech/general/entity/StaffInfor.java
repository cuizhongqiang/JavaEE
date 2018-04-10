package com.cnbmtech.general.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.cnbmtech.base.entity.BaseEntity;

/**
 * 员工信息
 * 
 * @author wxz
 */
@Entity
@Table(name = "staff_infor")
@DynamicUpdate
@DynamicInsert
public class StaffInfor extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号
	 */
	@Column
	@Excel(name = "编号")
	private String sysCode;
	

	/**
	 * 姓名
	 */
	@Column
	@Excel(name = "姓名")
	private String realName;

	/**
	 * 部门
	 */
	@Column
	@Excel(name = "部门", width = 20)
	private String department;

	/**
	 * Title（对外）
	 */
	@Column
	@Excel(name = "Title（对外）")
	private String title;

	/**
	 * 电话
	 */
	@Column
	@Excel(name = "电话", width = 20)
	private String telphone;

	/**
	 * 手机
	 */
	@Column
	@Excel(name = "手机", width = 20)
	private String mobile;

	/**
	 * 电子邮箱
	 */
	@Column
	@Excel(name = "邮箱", width = 20)
	private String email;

	/**
	 * 公司名称
	 */
	@Column
	@Excel(name = "公司名称", width = 40)
	private String compName;

	/**
	 * 公司地址
	 */
	@Column
	@Excel(name = "公司地址", width = 20)
	private String address;

	/**
	 * 网址
	 */
	@Column
	@Excel(name = "网址", width = 20)
	private String website;

	/**
	 * 传真
	 */
	@Column
	@Excel(name = "传真", width = 20)
	private String fax;

	/**
	 * 简介
	 */
	@Column
	private Long relationBrief;

	/**
	 * 备注
	 */
	@Column
	private String comment;

	/**
	 * 图片accid
	 */
	@Column
	private Long accId;

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public Long getRelationBrief() {
		return relationBrief;
	}

	public void setRelationBrief(Long relationBrief) {
		this.relationBrief = relationBrief;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

}
