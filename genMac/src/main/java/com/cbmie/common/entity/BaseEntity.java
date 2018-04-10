package com.cbmie.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public abstract class BaseEntity {
	
	/**
	 * 流程字段
	 * */
	private String userId;
	private String processInstanceId;
	
	@Column(name = "USERID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "PROCESSINSTANCEID")
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	/**
	 * 实体公共字段 
	 * */
	private Long id;
	private String createrNo;//创建人编号
	private String createrName;//创建人姓名
	private String createrDept;//创建人部门
	private Integer deptId;
	private Integer companyId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createDate;//创建时间
	
	private String updaterNo;//修改人编号
	private String updaterName;//修改人姓名
	private String updaterDept;//修改人部门
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateDate;//修改时间
	
	private String summary; //业务摘要
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CREATERNO")
	public String getCreaterNo() {
		return createrNo;
	}

	public void setCreaterNo(String createrNo) {
		this.createrNo = createrNo;
	}

	@Column(name = "CREATERNAME")
	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	@Column(name = "CREATERDEPT")
	public String getCreaterDept() {
		return createrDept;
	}

	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}

	@Column(name = "CREATERDATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATERNO")
	public String getUpdaterNo() {
		return updaterNo;
	}

	public void setUpdaterNo(String updaterNo) {
		this.updaterNo = updaterNo;
	}

	@Column(name = "UPDATERNAME")
	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	@Column(name = "UPDATERDEPT")
	public String getUpdaterDept() {
		return updaterDept;
	}

	public void setUpdaterDept(String updaterDept) {
		this.updaterDept = updaterDept;
	}

	@Column(name = "UPDATERDATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "SUMMARY", columnDefinition = "varchar(500)")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "DEPTID")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	@Column(name = "COMPANYID")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
