package com.cbmie.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 日志entity
 */
@Entity
@Table(name = "log")
@DynamicUpdate @DynamicInsert
public class Log implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String operationCode;
	private String creater;
	private Timestamp createDate;
	private Integer type;
	private String os;
	private String browser;
	private String ip;
	private String mac;
	private Integer executeTime;
	private String description;
	private String requestParam;

	// Constructors

	/** default constructor */
	public Log() {
	}

	/** minimal constructor */
	public Log(String operationCode, Timestamp createDate) {
		this.operationCode = operationCode;
		this.createDate = createDate;
	}

	/** full constructor */
	public Log(String operationCode, String creater, Timestamp createDate,
			Integer type, String os, String browser, String ip, String mac,
			Integer executeTime, String description, String requestParam) {
		this.operationCode = operationCode;
		this.creater = creater;
		this.createDate = createDate;
		this.type = type;
		this.os = os;
		this.browser = browser;
		this.ip = ip;
		this.mac = mac;
		this.executeTime = executeTime;
		this.description = description;
		this.requestParam = requestParam;
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

	@Column(name = "OPERATION_CODE", nullable = false, length = 50)
	public String getOperationCode() {
		return this.operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	@Column(name = "CREATER", length = 20)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "CREATE_DATE", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "OS", length = 20)
	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "BROWSER", length = 20)
	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name = "IP", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "MAC", length = 20)
	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "EXECUTE_TIME")
	public Integer getExecuteTime() {
		return this.executeTime;
	}

	public void setExecuteTime(Integer executeTime) {
		this.executeTime = executeTime;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "REQUEST_PARAM", length = 500)
	public String getRequestParam() {
		return this.requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

}