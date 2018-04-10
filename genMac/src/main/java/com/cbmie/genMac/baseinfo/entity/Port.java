package com.cbmie.genMac.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 港口
 */
@Entity
@Table(name = "port")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Port extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  港口编码
	 */
	private String portId;
	/**
	 * 港口名称
	 */
	private String portName;
	/**
	 * 简码
	 */
	private String portCode;
	/**
	 * 港口英文名
	 */
	private String portEnName;
	/**
	 * 区域码
	 */
	private String areaCode;
	/**
	 * 港口信息的状态：0正常、1停用
	 */
	private Integer status;
	/**
	 * 备注 
	 */
	private String comments; 
	
	@Column(name = "PORT_Id", nullable = false)
	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}


	@Column(name = "PORT_NAME", nullable = false)
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	@Column(name = "PORT_CODE", nullable = false)
	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	@Column(name = "PORT_EN_NAME", nullable = false)
	public String getPortEnName() {
		return portEnName;
	}

	public void setPortEnName(String portEnName) {
		this.portEnName = portEnName;
	}

	@Column(name = "AREA_CODE", nullable = false)
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}