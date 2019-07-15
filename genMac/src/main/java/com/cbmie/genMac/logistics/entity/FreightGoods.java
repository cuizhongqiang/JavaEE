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
 * 货代商品
 * @author czq
 */
@Entity
@Table(name = "freight_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class FreightGoods implements java.io.Serializable {

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
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 箱量
	 */
	private Double chest;
	
	/**
	 * 箱型
	 */
	private String chestType;
	
	/**
	 * 散杂货
	 */
	private String stray;

	/**
	 * 备注
	 */
	private String remark;
	
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

	@Column
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Column
	public Double getChest() {
		return chest;
	}

	public void setChest(Double chest) {
		this.chest = chest;
	}

	@Column
	public String getChestType() {
		return chestType;
	}

	public void setChestType(String chestType) {
		this.chestType = chestType;
	}

	@Column
	public String getStray() {
		return stray;
	}

	public void setStray(String stray) {
		this.stray = stray;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
