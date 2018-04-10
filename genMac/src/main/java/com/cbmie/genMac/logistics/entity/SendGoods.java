package com.cbmie.genMac.logistics.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 放货
 * @author czq
 */
@Entity
@Table(name = "send_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SendGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 发货单号
	 */
	private String sendGoodsNo;
	/**
	 * 提单号
	 */
	private String invoiceNo;
	/**
	 * 合同号
	 */
	private String contractNo;
	/**
	 * 入库单号
	 */
	private String inStockId;
	/**
	 * 仓储单位
	 */
	private String warehouse;
	/**
	 * 仓储地址
	 */
	private String warehouseAddress;
	/**
	 * 仓储邮编
	 */
	private String warehouseZipcode;
	/**
	 * 仓储联系人
	 */
	private String warehouseContacts;
	/**
	 * 仓储联系人电话
	 */
	private String warehouseContactsPhone;
	/**
	 * 仓储联系人传真
	 */
	private String warehouseContactsFax;
	/**
	 * 提货单位
	 */
	private String getGoodsUnit;
	/**
	 * 提货单位地址
	 */
	private String getGoodsUnitAddress;
	/**
	 * 提货单位邮编
	 */
	private String getGoodsUnitZipcode;
	/**
	 * 提货联系人
	 */
	private String getGoodsContacts;
	/**
	 * 提货联系人电话
	 */
	private String getGoodsContactsPhone;
	/**
	 * 提货联系人传真
	 */
	private String getGoodsContactsFax;
	/**
	 * 签发日期
	 */
	private Date sendDate;
	/**
	 * 本单合计(人民币)
	 */
	private Double totalMoney;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String state = "草稿";
	/**
	 * 放货商品
	 */
	private List<SendGoodsGoods> sendGoodsGoods = new ArrayList<SendGoodsGoods>();

	@Column(name = "SEND_GOODS_NO", nullable = false)
	public String getSendGoodsNo() {
		return sendGoodsNo;
	}

	public void setSendGoodsNo(String sendGoodsNo) {
		this.sendGoodsNo = sendGoodsNo;
	}

	@Column(name = "INVOICE_NO", nullable = false)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "CONTRACT_NO", nullable = false)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "IN_STOCK_ID")
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	@Column
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "WAREHOUSE_ADDRESS")
	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	@Column(name = "WAREHOUSE_ZIPCODE")
	public String getWarehouseZipcode() {
		return warehouseZipcode;
	}

	public void setWarehouseZipcode(String warehouseZipcode) {
		this.warehouseZipcode = warehouseZipcode;
	}

	@Column(name = "WAREHOUSE_CONTACTS")
	public String getWarehouseContacts() {
		return warehouseContacts;
	}

	public void setWarehouseContacts(String warehouseContacts) {
		this.warehouseContacts = warehouseContacts;
	}

	@Column(name = "WAREHOUSE_CONTACTS_PHONE")
	public String getWarehouseContactsPhone() {
		return warehouseContactsPhone;
	}

	public void setWarehouseContactsPhone(String warehouseContactsPhone) {
		this.warehouseContactsPhone = warehouseContactsPhone;
	}

	@Column(name = "WAREHOUSE_CONTACTS_FAX")
	public String getWarehouseContactsFax() {
		return warehouseContactsFax;
	}

	public void setWarehouseContactsFax(String warehouseContactsFax) {
		this.warehouseContactsFax = warehouseContactsFax;
	}

	@Column(name = "GET_GOODS_UNIT", nullable = false)
	public String getGetGoodsUnit() {
		return getGoodsUnit;
	}

	public void setGetGoodsUnit(String getGoodsUnit) {
		this.getGoodsUnit = getGoodsUnit;
	}

	@Column(name = "GET_GOODS_UNIT_ADDRESS")
	public String getGetGoodsUnitAddress() {
		return getGoodsUnitAddress;
	}

	public void setGetGoodsUnitAddress(String getGoodsUnitAddress) {
		this.getGoodsUnitAddress = getGoodsUnitAddress;
	}

	@Column(name = "GET_GOODS_UNIT_ZIPCODE")
	public String getGetGoodsUnitZipcode() {
		return getGoodsUnitZipcode;
	}

	public void setGetGoodsUnitZipcode(String getGoodsUnitZipcode) {
		this.getGoodsUnitZipcode = getGoodsUnitZipcode;
	}

	@Column(name = "GET_GOODS_CONTACTS")
	public String getGetGoodsContacts() {
		return getGoodsContacts;
	}

	public void setGetGoodsContacts(String getGoodsContacts) {
		this.getGoodsContacts = getGoodsContacts;
	}

	@Column(name = "GET_GOODS_CONTACTS_PHONE")
	public String getGetGoodsContactsPhone() {
		return getGoodsContactsPhone;
	}

	public void setGetGoodsContactsPhone(String getGoodsContactsPhone) {
		this.getGoodsContactsPhone = getGoodsContactsPhone;
	}

	@Column(name = "GET_GOODS_CONTACTS_FAX")
	public String getGetGoodsContactsFax() {
		return getGoodsContactsFax;
	}

	public void setGetGoodsContactsFax(String getGoodsContactsFax) {
		this.getGoodsContactsFax = getGoodsContactsFax;
	}

	@Column(name = "SEND_DATE")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "TOTAL_MONEY")
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<SendGoodsGoods> getSendGoodsGoods() {
		return sendGoodsGoods;
	}

	public void setSendGoodsGoods(List<SendGoodsGoods> sendGoodsGoods) {
		this.sendGoodsGoods = sendGoodsGoods;
	}

}
