package com.cbmie.genMac.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cbmie.common.entity.BaseEntity;

/**
 */
@Entity
@Table(name = "goods")
public class Goods extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 实体字段
	 * */
//	private GoodsType goodsType;
	private Long goodsTypeId;
	private String name;
	private String cover;
	private String img;
	private Double price;
	private Double marketPrice;
	private String introduce;
	private String brief;
	private String isSold;
	private Double sales;
	private Double postage;
	private Integer pv;
	private String state ="草稿";
	private List<GoodsChild> goodsChild = new ArrayList<GoodsChild>();

	@Column(name = "GOODSTYPEID")
	public Long getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Long goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "TYPE_ID", nullable = false)
//	public GoodsType getGoodsType() {
//		return this.goodsType;
//	}
	
//	public void setGoodsType(GoodsType goodsType) {
//		this.goodsType = goodsType;
//	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "COVER", nullable = false)
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Column(name = "IMG", nullable = false)
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "PRICE", nullable = false, precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "MARKET_PRICE", precision = 22, scale = 0)
	public Double getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Column(name = "INTRODUCE")
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "BRIEF")
	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "IS_SOLD")
	public String getIsSold() {
		return this.isSold;
	}

	public void setIsSold(String isSold) {
		this.isSold = isSold;
	}

	@Column(name = "SALES")
	public Double getSales() {
		return this.sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	@Column(name = "POSTAGE", precision = 22, scale = 0)
	public Double getPostage() {
		return this.postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	@Column(name = "PV")
	public Integer getPv() {
		return this.pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<GoodsChild> getGoodsChild() {
		return goodsChild;
	}
	
	public void setGoodsChild(List<GoodsChild> goodsChild) {
		this.goodsChild = goodsChild;
	}
	
}