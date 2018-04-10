package com.cbmie.genMac.stock.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 盘库
 */
@Entity
@Table(name = "plan_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PlanStock implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String createrNo;//创建人编号
	private String createrName;//创建人姓名
	private String createrDept;//创建人部门
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createDate;//创建时间
	
	private String updaterNo;//修改人编号
	private String updaterName;//修改人姓名
	private String updaterDept;//修改人部门
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateDate;//修改时间
	
	List<PlanStockDetail> planStockDetail = new ArrayList<PlanStockDetail>();
	
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<PlanStockDetail> getPlanStockDetail() {
		return planStockDetail;
	}

	public void setPlanStockDetail(List<PlanStockDetail> planStockDetail) {
		this.planStockDetail = planStockDetail;
	}
	
}