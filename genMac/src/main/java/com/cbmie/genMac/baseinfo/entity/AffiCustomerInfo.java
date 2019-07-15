package com.cbmie.genMac.baseinfo.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 关联单位__客户评审
 */
@Entity
@Table(name = "AFFI_CUSTOMER_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AffiCustomerInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 授信业务额度
	 */
	private Double creditLine;

	/**
	 * 评审有效开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date checkStartTime;

	/**
	 * 评审有效结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date checkEndTime;

	/**
	 * 客户简介及生产经营情况等
	 */
	private String customerAndConditions;
	
	/**
	 * 关联id
	 */
	private String parentId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "CREDIT_LINE")
	public Double getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(Double creditLine) {
		this.creditLine = creditLine;
	}

	@Column(name = "CHECK_START_TIME")
	public Date getCheckStartTime() {
		return checkStartTime;
	}

	public void setCheckStartTime(Date checkStartTime) {
		this.checkStartTime = checkStartTime;
	}

	@Column(name = "CHECK_END_TIME")
	public Date getCheckEndTime() {
		return checkEndTime;
	}

	public void setCheckEndTime(Date checkEndTime) {
		this.checkEndTime = checkEndTime;
	}

	@Column(name = "CUSTOMER_AND_CONDITIONS")
	public String getCustomerAndConditions() {
		return customerAndConditions;
	}

	public void setCustomerAndConditions(String customerAndConditions) {
		this.customerAndConditions = customerAndConditions;
	}

}