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
 * 盘库明细
 */
@Entity
@Table(name = "plan_stock_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PlanStockDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	/**
	 * 仓库名称
	 */
	private String warehouseName;
	
	/**
	 * 商品大类名称及规格型号
	 */
	private String goodsNameSpecification;
	
	/**
	 * 账面数
	 */
	private Double bookNum;
	
	/**
	 * 盘盈
	 */
	private Double profitNum;
	
	/**
	 * 盘亏
	 */
	private Double lossNum;
	
	/**
	 * 盘点数
	 */
	private Double inventoryNum;
	
	/**
	 * 盘盈盘亏差异说明
	 */
	private String diffInstruction;
	
	/**
	 * 备注
	 */
	private String remark;
	
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

	@Column(name = "WAREHOUSE_NAME")
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column(name = "GOODS_NAME_SPECIFICATION")
	public String getGoodsNameSpecification() {
		return goodsNameSpecification;
	}

	public void setGoodsNameSpecification(String goodsNameSpecification) {
		this.goodsNameSpecification = goodsNameSpecification;
	}

	@Column(name = "BOOK_NUM")
	public Double getBookNum() {
		return bookNum;
	}

	public void setBookNum(Double bookNum) {
		this.bookNum = bookNum;
	}

	@Column(name = "PROFIT_NUM")
	public Double getProfitNum() {
		return profitNum;
	}

	public void setProfitNum(Double profitNum) {
		this.profitNum = profitNum;
	}

	@Column(name = "LOSS_NUM")
	public Double getLossNum() {
		return lossNum;
	}

	public void setLossNum(Double lossNum) {
		this.lossNum = lossNum;
	}

	@Column(name = "INVENTORY_NUM")
	public Double getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Double inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	@Column(name = "DIFF_INSTRUCTION")
	public String getDiffInstruction() {
		return diffInstruction;
	}

	public void setDiffInstruction(String diffInstruction) {
		this.diffInstruction = diffInstruction;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}