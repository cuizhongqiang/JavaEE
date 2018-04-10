package com.cbmie.genMac.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 汇率
 */
@Entity
@Table(name = "exchange_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ExchangeRate extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	 private String showTime; 
	 
	 @Column(name = "SHOW_TIME", nullable = false) 
	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 对本币汇率
	 */
	private double exchangeRateSelf;

	/**
	 * 美元汇率
	 */
	private double exchangeRateUS;

	/**
	 * 对本币汇率买入价
	 */
	private double buyingRateSelf;

	/**
	 * 对美元汇率买入价
	 */
	private double buyingRateUS;

	/**
	 * 对本币汇率卖出价
	 */
	private double sellingRateSelf;

	/**
	 * 对美元汇率卖出价
	 */
	private double sellingRateUS;


	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "EXCHANGE_RATE_SELF", nullable = false)
	public double getExchangeRateSelf() {
		return exchangeRateSelf;
	}

	public void setExchangeRateSelf(double exchangeRateSelf) {
		this.exchangeRateSelf = exchangeRateSelf;
	}

	@Column(name = "EXCHANGE_RATE_US", nullable = false)
	public double getExchangeRateUS() {
		return exchangeRateUS;
	}

	public void setExchangeRateUS(double exchangeRateUS) {
		this.exchangeRateUS = exchangeRateUS;
	}

	@Column(name = "BUYING_RATE_SELF", nullable = false)
	public double getBuyingRateSelf() {
		return buyingRateSelf;
	}

	public void setBuyingRateSelf(double buyingRateSelf) {
		this.buyingRateSelf = buyingRateSelf;
	}

	@Column(name = "BUTING_RATE_US", nullable = false)
	public double getBuyingRateUS() {
		return buyingRateUS;
	}

	public void setBuyingRateUS(double buyingRateUS) {
		this.buyingRateUS = buyingRateUS;
	}

	@Column(name = "SELLING_RATE_SELF", nullable = false)
	public double getSellingRateSelf() {
		return sellingRateSelf;
	}

	public void setSellingRateSelf(double sellingRateSelf) {
		this.sellingRateSelf = sellingRateSelf;
	}

	@Column(name = "SELLING_RATE_US", nullable = false)
	public double getSellingRateUS() {
		return sellingRateUS;
	}

	public void setSellingRateUS(double sellingRateUS) {
		this.sellingRateUS = sellingRateUS;
	}

}