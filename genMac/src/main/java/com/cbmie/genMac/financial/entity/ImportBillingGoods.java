package com.cbmie.genMac.financial.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 进口结算联系单_商品
 */
@Entity
@Table(name = "import_billing_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ImportBillingGoods implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 货物（应税劳务）名称
	 */
	private String goodsCategory;
	
	/**
	 * 规格
	 */
	private String specification;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 销售金额
	 */
	private Double salesAmount;
	
	/**
	 * 税率
	 */
	private Double rateMain;
	
	/**
	 * 税额
	 */
	private Double taxMain;
	
	private Long pid;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GOODS_CATEGORY")
	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	@Column
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "SALES_AMOUNT")
	public Double getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}

	@Column(name = "RATE_MAIN")
	public Double getRateMain() {
		return rateMain;
	}

	public void setRateMain(Double rateMain) {
		this.rateMain = rateMain;
	}

	@Column(name = "TAX_MAIN")
	public Double getTaxMain() {
		return taxMain;
	}

	public void setTaxMain(Double taxMain) {
		this.taxMain = taxMain;
	}

	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
}