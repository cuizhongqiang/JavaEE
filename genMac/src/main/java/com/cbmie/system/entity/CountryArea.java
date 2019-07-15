package com.cbmie.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 国别区域entity
 */
@Entity
@Table(name = "country_area")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class CountryArea implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer pid;
	private String name; //名称
	private String code; //编码
	private String scode; //简码
	private String registrant; //登记人
	private String registrantDept; //登记部门
	private Date registrantDate; //登记时间

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PID")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SCODE")
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	@Column(name = "REGISTRANT")
	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	@Column(name = "REGISTRANTDEPT")
	public String getRegistrantDept() {
		return registrantDept;
	}

	public void setRegistrantDept(String registrantDept) {
		this.registrantDept = registrantDept;
	}

	@Column(name = "REGISTRANTDATE")
	public Date getRegistrantDate() {
		return registrantDate;
	}

	public void setRegistrantDate(Date registrantDate) {
		this.registrantDate = registrantDate;
	}

}