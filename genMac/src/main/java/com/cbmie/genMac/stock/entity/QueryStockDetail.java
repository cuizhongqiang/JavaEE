package com.cbmie.genMac.stock.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 库存商品明细
 */
public class QueryStockDetail {

	/**
	 * 商品名称/规格型号
	 */
	private String goodsNameSpecification;
	
	/**
	 * 仓库名称
	 */
	private String warehouseName;
	
	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 入库编号
	 */
	private String inStockId;
	
	/**
	 * 入库数量
	 */
	private Double inStockAmount;
	
	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date inStockDate;
	
	/**
	 * 发货单号
	 */
	private String sendGoodsNo;
	
	/**
	 * 发货数量
	 */
	private Double sendGoodsAmount;
	
	/**
	 * 签发日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date sendDate;
	
	public String getGoodsNameSpecification() {
		return goodsNameSpecification;
	}

	public void setGoodsNameSpecification(String goodsNameSpecification) {
		this.goodsNameSpecification = goodsNameSpecification;
	}
	
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	public Double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(Double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	public String getSendGoodsNo() {
		return sendGoodsNo;
	}

	public void setSendGoodsNo(String sendGoodsNo) {
		this.sendGoodsNo = sendGoodsNo;
	}

	public Double getSendGoodsAmount() {
		return sendGoodsAmount;
	}

	public void setSendGoodsAmount(Double sendGoodsAmount) {
		this.sendGoodsAmount = sendGoodsAmount;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}