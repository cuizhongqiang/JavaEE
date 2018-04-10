package com.cbmie.genMac.stock.entity;

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
 * 入库商品
 */
@Entity
@Table(name = "in_stock_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStockGoods implements java.io.Serializable {

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
	 * 父id
	 */
	private Long parentId;
	
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

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
