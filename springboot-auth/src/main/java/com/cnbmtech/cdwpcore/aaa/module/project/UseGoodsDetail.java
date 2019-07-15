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
public class UseGoodsDetail {
	@Id
	@GeneratedValue
	@ApiModelProperty(value = "编号")
	Long id;
	@ApiModelProperty(value = "project编号")
	Long projectId;
	@ApiModelProperty(value = "品名")
	String articleName;
	@ApiModelProperty(value = "规格型号")
	String standard;
	@ApiModelProperty(value = "数量")
	Double amounts;
	@ApiModelProperty(value = "报价")
	Double offer;
	@ApiModelProperty(value = "运费补贴")
	Double freightAllowance;
	@ApiModelProperty(value = "财务费用补贴")
	Double financialAllowance;
	@ApiModelProperty(value = "采购价(含一票制运费吊装费)")
	Double purchasePrice;
	@ApiModelProperty(value = "运费")
	Double freightFee;
	@ApiModelProperty(value = "吊装费")
	Double hoistingFee;
	@ApiModelProperty(value = "财务费用")
	Double financialFee;
	@ApiModelProperty(value = "其他成本费用")
	Double otherFee;
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
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Double getAmounts() {
		return amounts;
	}
	public void setAmounts(Double amounts) {
		this.amounts = amounts;
	}
	public Double getOffer() {
		return offer;
	}
	public void setOffer(Double offer) {
		this.offer = offer;
	}
	public Double getFreightAllowance() {
		return freightAllowance;
	}
	public void setFreightAllowance(Double freightAllowance) {
		this.freightAllowance = freightAllowance;
	}
	public Double getFinancialAllowance() {
		return financialAllowance;
	}
	public void setFinancialAllowance(Double financialAllowance) {
		this.financialAllowance = financialAllowance;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Double getFreightFee() {
		return freightFee;
	}
	public void setFreightFee(Double freightFee) {
		this.freightFee = freightFee;
	}
	public Double getHoistingFee() {
		return hoistingFee;
	}
	public void setHoistingFee(Double hoistingFee) {
		this.hoistingFee = hoistingFee;
	}
	public Double getFinancialFee() {
		return financialFee;
	}
	public void setFinancialFee(Double financialFee) {
		this.financialFee = financialFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
}
