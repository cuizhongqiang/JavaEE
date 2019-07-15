package com.cbmie.genMac.logistics.entity;

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
 * 到单商品
 * @author czq
 */
@Entity
@Table(name = "invoice_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceGoods implements java.io.Serializable {

	private static final long serialVersionUID = -531529171875368614L;
	
	private Long id;

	/**
	 * 商品大类
	 */
	private String goodsCategory;

	/**
	 * 规格型号
	 */
	private String specification;
	
	/**
	 * 车架号
	 */
	private String frameNo;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 单价
	 */
	private Double price;

	/**
	 * 原币金额
	 */
	private Double original;

	/**
	 * 关税税额
	 */
	private Double tax;

	/**
	 * 关税税率
	 */
	private Double taxRate;

	/**
	 * 消费税额
	 */
	private Double saleTax;

	/**
	 * 消费税率
	 */
	private Double saleTaxRate;

	/**
	 * 增值税额
	 */
	private Double vat;

	/**
	 * 增值税率
	 */
	private Double vatRate;
	
	/**
	 * 父id
	 */
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
	
	@Column(name = "GOODS_CATEGORY", nullable = false)
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

	@Column(name = "FRAME_NO")
	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	@Column
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column
	public Double getOriginal() {
		return original;
	}

	public void setOriginal(Double original) {
		this.original = original;
	}

	@Column
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Column(name = "TAX_RATE")
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "SALE_TAX")
	public Double getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(Double saleTax) {
		this.saleTax = saleTax;
	}

	@Column(name = "SALE_TAX_RATE")
	public Double getSaleTaxRate() {
		return saleTaxRate;
	}

	public void setSaleTaxRate(Double saleTaxRate) {
		this.saleTaxRate = saleTaxRate;
	}

	@Column(name = "VAT")
	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	@Column(name = "VAT_RATE")
	public Double getVatRate() {
		return vatRate;
	}

	public void setVatRate(Double vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "pid")
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
