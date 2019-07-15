package com.cnbmtech.cdwpcore.aaa.module.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ClassName: Project
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wy
 * @date 2018年2月6日
 *
 */
@Entity
@ApiModel
public class ReceiveDetail {
	@Id
	@GeneratedValue
	@ApiModelProperty(value = "编号")
	Long id;
	@ApiModelProperty(value = "project编号")
	Long projectId;
	@ApiModelProperty(value = "回款批次")
	String receiveBatch;
	@ApiModelProperty(value = "回款比例")
	Double receiveRate;
	@ApiModelProperty(value = "回款天数")
	Double receiveDays;
	@ApiModelProperty(value = "备注")
	String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getReceiveBatch() {
		return receiveBatch;
	}
	public void setReceiveBatch(String receiveBatch) {
		this.receiveBatch = receiveBatch;
	}
	public Double getReceiveRate() {
		return receiveRate;
	}
	public void setReceiveRate(Double receiveRate) {
		this.receiveRate = receiveRate;
	}
	public Double getReceiveDays() {
		return receiveDays;
	}
	public void setReceiveDays(Double receiveDays) {
		this.receiveDays = receiveDays;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
