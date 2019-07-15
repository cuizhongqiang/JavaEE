
/**  
* @Title: User.java
* @Package com.cnbmtech.cdwpcore.aaa.jpa
* @Description: TODO(用一句话描述该文件做什么)
* @author markzgwu
* @date 2017年12月14日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.project;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: Project
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author markzgwu
 * @date 2017年12月14日
 *
 */
@Entity
@ApiModel
@Table(indexes = { @Index(name = "index_0", columnList = "projectName", unique = true),
		@Index(name = "index_1", columnList = "ourCompany", unique = false) })
public class Project {

	@Id
	@GeneratedValue
	@ApiModelProperty(value = "编号")
	Long id;
	@ApiModelProperty(value = "项目名称")
	String projectName;
	@ApiModelProperty(value = "我司单位")
	String ourCompany;
	@ApiModelProperty(value = "业务负责人")
	String businessPerson;
	@ApiModelProperty(value = "品种")
	String varieties;
	@ApiModelProperty(value = "承建方")
	String contractor;
	@ApiModelProperty(value = "项目地点")
	String projectAddress;
	@ApiModelProperty(value = "配送模式")
	String dispatchingModel;
	@ApiModelProperty(value = "运费类别")
	String freigthCategory;
	@ApiModelProperty(value = "合作利润分配方式")
	String cooperateProfitModel;
	@ApiModelProperty(value = "中建材利润分配比例")
	String cnbmProfitRate;
	@ApiModelProperty(value = "项目性质")
	String projectProperty;
	@ApiModelProperty(value = "项目确定方式")
	String projectConfirmModel;
	@ApiModelProperty(value = "业主/建设单位")
	String businessUnit;
	@ApiModelProperty(value = "资金背景/来源")
	String source;
	@ApiModelProperty(value = "项目总量（吨）")
	Double projectAmount;
	@ApiModelProperty(value = "供货周期（天）")
	Double supplyCycle;
	@ApiModelProperty(value = "与承建方定价方式")
	String contractorFixModel;
	@ApiModelProperty(value = "项目最大赊销额度(万元)")
	Double projectSellAmount;
	@ApiModelProperty(value = "平均回款周期（天）")
	Double receivedPayments;
	@ApiModelProperty(value = "对账日")
	String accountDay;
	@ApiModelProperty(value = "结算方式及回款期限")
	String accountsAndReceivePeriod;
	@ApiModelProperty(value = "逾期回款条款")
	String receiveClause;
	@ApiModelProperty(value = "合作配送方")
	String distribution;
	@ApiModelProperty(value = "项目预估单吨收益(元/吨/不含税)")
	Double projectTRate;
	@ApiModelProperty(value = "预估项目整体收益(万元不含税)")
	Double projectTotalRate;
	@ApiModelProperty(value = "我司单吨收益")
	Double cnbmTRate;
	@ApiModelProperty(value = "我司整体收益")
	Double cnbmTotalRate;
	@ApiModelProperty(value = "合作方单吨收益")
	Double cooperateTRate;
	@ApiModelProperty(value = "合作方整体收益")
	Double cooperateTotalRate;
	@ApiModelProperty(value = "配送方保证金比例")
	Double despatchDepositRate;
	@ApiModelProperty(value = "备注")
	String remark;
	@ApiModelProperty(value = "登记人")
	String createrName;
	@ApiModelProperty(value = "登记部门")
	String createrDept;
	@ApiModelProperty(value = "登记时间")
	Date createDate;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOurCompany() {
		return ourCompany;
	}

	public void setOurCompany(String ourCompany) {
		this.ourCompany = ourCompany;
	}

	public String getBusinessPerson() {
		return businessPerson;
	}

	public void setBusinessPerson(String businessPerson) {
		this.businessPerson = businessPerson;
	}

	public String getVarieties() {
		return varieties;
	}

	public void setVarieties(String varieties) {
		this.varieties = varieties;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getDispatchingModel() {
		return dispatchingModel;
	}

	public void setDispatchingModel(String dispatchingModel) {
		this.dispatchingModel = dispatchingModel;
	}

	public String getFreigthCategory() {
		return freigthCategory;
	}

	public void setFreigthCategory(String freigthCategory) {
		this.freigthCategory = freigthCategory;
	}

	public String getCooperateProfitModel() {
		return cooperateProfitModel;
	}

	public void setCooperateProfitModel(String cooperateProfitModel) {
		this.cooperateProfitModel = cooperateProfitModel;
	}

	public String getCnbmProfitRate() {
		return cnbmProfitRate;
	}

	public void setCnbmProfitRate(String cnbmProfitRate) {
		this.cnbmProfitRate = cnbmProfitRate;
	}

	public String getProjectProperty() {
		return projectProperty;
	}

	public void setProjectProperty(String projectProperty) {
		this.projectProperty = projectProperty;
	}

	public String getProjectConfirmModel() {
		return projectConfirmModel;
	}

	public void setProjectConfirmModel(String projectConfirmModel) {
		this.projectConfirmModel = projectConfirmModel;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Double getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(Double projectAmount) {
		this.projectAmount = projectAmount;
	}

	public Double getSupplyCycle() {
		return supplyCycle;
	}

	public void setSupplyCycle(Double supplyCycle) {
		this.supplyCycle = supplyCycle;
	}

	public String getContractorFixModel() {
		return contractorFixModel;
	}

	public void setContractorFixModel(String contractorFixModel) {
		this.contractorFixModel = contractorFixModel;
	}

	public Double getProjectSellAmount() {
		return projectSellAmount;
	}

	public void setProjectSellAmount(Double projectSellAmount) {
		this.projectSellAmount = projectSellAmount;
	}

	public Double getReceivedPayments() {
		return receivedPayments;
	}

	public void setReceivedPayments(Double receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}

	public String getAccountsAndReceivePeriod() {
		return accountsAndReceivePeriod;
	}

	public void setAccountsAndReceivePeriod(String accountsAndReceivePeriod) {
		this.accountsAndReceivePeriod = accountsAndReceivePeriod;
	}

	public String getReceiveClause() {
		return receiveClause;
	}

	public void setReceiveClause(String receiveClause) {
		this.receiveClause = receiveClause;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public Double getProjectTRate() {
		return projectTRate;
	}

	public void setProjectTRate(Double projectTRate) {
		this.projectTRate = projectTRate;
	}

	public Double getProjectTotalRate() {
		return projectTotalRate;
	}

	public void setProjectTotalRate(Double projectTotalRate) {
		this.projectTotalRate = projectTotalRate;
	}

	public Double getCnbmTRate() {
		return cnbmTRate;
	}

	public void setCnbmTRate(Double cnbmTRate) {
		this.cnbmTRate = cnbmTRate;
	}

	public Double getCnbmTotalRate() {
		return cnbmTotalRate;
	}

	public void setCnbmTotalRate(Double cnbmTotalRate) {
		this.cnbmTotalRate = cnbmTotalRate;
	}

	public Double getCooperateTRate() {
		return cooperateTRate;
	}

	public void setCooperateTRate(Double cooperateTRate) {
		this.cooperateTRate = cooperateTRate;
	}

	public Double getCooperateTotalRate() {
		return cooperateTotalRate;
	}

	public void setCooperateTotalRate(Double cooperateTotalRate) {
		this.cooperateTotalRate = cooperateTotalRate;
	}

	public Double getDespatchDepositRate() {
		return despatchDepositRate;
	}

	public void setDespatchDepositRate(Double despatchDepositRate) {
		this.despatchDepositRate = despatchDepositRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterDept() {
		return createrDept;
	}

	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
