package com.cbmie.genMac.baseinfo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 商品管理
 */
@Entity
@Table(name = "goods_manage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class GoodsManage extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品大类码
	 */
	private String goodsCode;

	/**
	 * 商品大类名称
	 */
	private String goodsName;

	/**
	 * 财务商品码
	 */
	private String financialCode;
	
	/**
	 * 统计商品码
	 */
	private String statisticalCode;

	/**
	 * 统计单位
	 */
	private String statisticalUnit;

	/**
	 * 增值税率(单位：%)
	 */
	private Double vat;
	
	/**
	 * 退税率(单位：%)
	 */
	private Double taxRebate;
	
	/**
	 * 出口关税率(单位：%)
	 */
	private Double exportTariffs;
	
	/**
	 * 出口销项税率(单位：%)
	 */
	private Double outputTax;
	
	/**
	 * 普通进口关税率(单位：%)
	 */
	private Double ordinaryImportTariffs;

	/**
	 * 最惠国进口关税率(单位：%)
	 */
	private Double mostFavoredNationImportTariffs;
	
	/**
	 * 消费税率(单位：%)
	 */
	private Double consumptionTax;
	
	/**
	 * 特别关税
	 */
	private String specialTariffs;
	
	/**
	 * 终止年月
	 */
	private Date endTime;

	/**
	 * 属性一
	 */
	private String attrOne;

	/**
	 * 属性二
	 */
	private String attrTwo;
	
	private Long pid;

	@Column(name = "GOODSCODE")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	@Column(name = "GOODSNAME")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "FINANCIAL_CODE")
	public String getFinancialCode() {
		return financialCode;
	}

	public void setFinancialCode(String financialCode) {
		this.financialCode = financialCode;
	}

	@Column(name = "STATISTICAL_CODE")
	public String getStatisticalCode() {
		return statisticalCode;
	}

	public void setStatisticalCode(String statisticalCode) {
		this.statisticalCode = statisticalCode;
	}

	@Column(name = "STATISTICALUNIT")
	public String getStatisticalUnit() {
		return statisticalUnit;
	}

	public void setStatisticalUnit(String statisticalUnit) {
		this.statisticalUnit = statisticalUnit;
	}

	@Column(name = "VAT")
	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	@Column(name = "TAXREBATE")
	public Double getTaxRebate() {
		return taxRebate;
	}

	public void setTaxRebate(Double taxRebate) {
		this.taxRebate = taxRebate;
	}

	@Column(name = "EXPORTTARIFFS")
	public Double getExportTariffs() {
		return exportTariffs;
	}

	public void setExportTariffs(Double exportTariffs) {
		this.exportTariffs = exportTariffs;
	}

	@Column(name = "OUTPUTTAX")
	public Double getOutputTax() {
		return outputTax;
	}

	public void setOutputTax(Double outputTax) {
		this.outputTax = outputTax;
	}

	@Column(name = "ORDINARYIMPORTTARIFFS")
	public Double getOrdinaryImportTariffs() {
		return ordinaryImportTariffs;
	}

	public void setOrdinaryImportTariffs(Double ordinaryImportTariffs) {
		this.ordinaryImportTariffs = ordinaryImportTariffs;
	}

	@Column(name = "MOSTFAVOREDNATIONIMPORTTARIFFS")
	public Double getMostFavoredNationImportTariffs() {
		return mostFavoredNationImportTariffs;
	}

	public void setMostFavoredNationImportTariffs(Double mostFavoredNationImportTariffs) {
		this.mostFavoredNationImportTariffs = mostFavoredNationImportTariffs;
	}

	@Column(name = "CONSUMPTIONTAX")
	public Double getConsumptionTax() {
		return consumptionTax;
	}

	public void setConsumptionTax(Double consumptionTax) {
		this.consumptionTax = consumptionTax;
	}

	@Column(name = "SPECIALTARIFFS")
	public String getSpecialTariffs() {
		return specialTariffs;
	}

	public void setSpecialTariffs(String specialTariffs) {
		this.specialTariffs = specialTariffs;
	}

	@Column(name = "ENDTIME", columnDefinition = "date")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "ATTRONE")
	public String getAttrOne() {
		return attrOne;
	}

	public void setAttrOne(String attrOne) {
		this.attrOne = attrOne;
	}

	@Column(name = "ATTRTWO")
	public String getAttrTwo() {
		return attrTwo;
	}

	public void setAttrTwo(String attrTwo) {
		this.attrTwo = attrTwo;
	}

	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}