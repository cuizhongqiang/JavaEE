package com.cbmie.genMac.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.PayTaxesDao;
import com.cbmie.genMac.financial.entity.PayTaxes;
import com.cbmie.genMac.logistics.dao.InvoiceRegDao;
import com.cbmie.genMac.logistics.dao.SendGoodsDao;
import com.cbmie.genMac.logistics.entity.InvoiceReg;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.stock.dao.InStockDao;
import com.cbmie.genMac.stock.entity.InStock;

/**
 * 到单登记service
 */
@Service
@Transactional
public class InvoiceRegService extends BaseService<InvoiceReg, Long> {

	@Autowired
	private InvoiceRegDao invoiceRegDao;
	
	@Autowired
	private PayTaxesDao payTaxesDao;
	
	@Autowired
	private InStockDao inStockDao;
	
	@Autowired
	private SendGoodsDao sendGoodsDao;

	@Override
	public HibernateDao<InvoiceReg, Long> getEntityDao() {
		return invoiceRegDao;
	}
	
	public InvoiceReg findByNo(Long id, String no){
		return invoiceRegDao.findByNo(id, no);
	}
	
	public String customsDeclarationTrace(Long id) {
		StringBuffer jsonBuffer = new StringBuffer();
		StringBuffer jsonChildBuffer = new StringBuffer();
		// 到单
		InvoiceReg invoiceReg = invoiceRegDao.get(id);
		jsonBuffer.append("{");
		jsonBuffer.append("'id':" + invoiceReg.getId());
		jsonBuffer.append(",'title':'到单(" + DateUtils.formatDate(invoiceReg.getArriveDate(), "yyyy-MM-dd") + ")'");
		jsonBuffer.append(",'dataNo':'" + invoiceReg.getInvoiceNo() + "'");
		jsonChildBuffer.append(",'children':[");
		// 到港
		if (invoiceReg.getLetDate() != null) {
			if (jsonChildBuffer.length() > 13) {jsonChildBuffer.append(",");} 
			jsonChildBuffer.append("{");
			jsonChildBuffer.append("'id':''");
			jsonChildBuffer.append(",'title':'到港(" + DateUtils.formatDate(invoiceReg.getArrivalPortDate(), "yyyy-MM-dd") + ")'");
			jsonChildBuffer.append(",'dataNo':'" + invoiceReg.getCustomsDeclarationNo() + "'");
			jsonChildBuffer.append("}");
		}
		// 交税
		List<PayTaxes> payTaxesList = payTaxesDao.findBy("invoiceNo", invoiceReg.getInvoiceNo());
		for (PayTaxes payTaxes : payTaxesList) {
			if (payTaxes.getState().equals("生效")) {
				if (jsonChildBuffer.length() > 13) {jsonChildBuffer.append(",");}
				jsonChildBuffer.append("{");
				jsonChildBuffer.append("'id':" + payTaxes.getId());
				jsonChildBuffer.append(",'title':'交税(" + DateUtils.formatDate(payTaxes.getPayDate(), "yyyy-MM-dd") + ")'");
				jsonChildBuffer.append(",'dataNo':'" + payTaxes.getTaxNo() + "'");
				jsonChildBuffer.append("}");
			}
		}
		// 放行
		if (invoiceReg.getLetDate() != null) {
			if (jsonChildBuffer.length() > 13) {jsonChildBuffer.append(",");} 
			jsonChildBuffer.append("{");
			jsonChildBuffer.append("'id':''");
			jsonChildBuffer.append(",'title':'放行(" + DateUtils.formatDate(invoiceReg.getLetDate(), "yyyy-MM-dd") + ")'");
			jsonChildBuffer.append(",'dataNo':'" + invoiceReg.getCustomsDeclarationNo() + "'");
			jsonChildBuffer.append("}");
		}
		// 入库
		List<InStock> inStockList = inStockDao.findBy("invoiceNo", invoiceReg.getInvoiceNo());
		for (InStock inStock : inStockList) {
			if (jsonChildBuffer.length() > 13) {jsonChildBuffer.append(",");}
			jsonChildBuffer.append("{");
			jsonChildBuffer.append("'id':" + inStock.getId());
			jsonChildBuffer.append(",'title':'入库(" + DateUtils.formatDate(inStock.getInStockDate(), "yyyy-MM-dd") + ")'");
			jsonChildBuffer.append(",'dataNo':'" + inStock.getInStockId() + "'");
			// 走库存送货
			List<SendGoods> sendGoodsList = sendGoodsDao.findBy("inStockId", inStock.getInStockId());
			for (int i = 0; i < sendGoodsList.size(); i++) {
				if (sendGoodsList.get(i).getState().equals("生效")) {
					if (i == 0) {
						jsonChildBuffer.append(",'children':[");
					} else {
						jsonChildBuffer.append(",");
					}
					jsonChildBuffer.append("{");
					jsonChildBuffer.append("'id':" + sendGoodsList.get(i).getId());
					jsonChildBuffer.append(",'title':'送货(" + DateUtils.formatDate(sendGoodsList.get(i).getSendDate(), "yyyy-MM-dd") + ")'");
					jsonChildBuffer.append(",'dataNo':'" + sendGoodsList.get(i).getSendGoodsNo() + "'");
					jsonChildBuffer.append("}");
					if (i + 1 == sendGoodsList.size()) {
						jsonChildBuffer.append("]");
					}
				}
			}
			jsonChildBuffer.append("}");
		}
		// 港口直发送货
		List<SendGoods> sendGoodsList = sendGoodsDao.findBy("invoiceNo", invoiceReg.getInvoiceNo());
		for (SendGoods sendGoods : sendGoodsList) {
			if (sendGoods.getState().equals("生效") && StringUtils.isBlank(sendGoods.getInStockId())) {
				if (jsonChildBuffer.length() > 13) {jsonChildBuffer.append(",");}
				jsonChildBuffer.append("{");
				jsonChildBuffer.append("'id':" + sendGoods.getId());
				jsonChildBuffer.append(",'title':'直发(" + DateUtils.formatDate(sendGoods.getSendDate(), "yyyy-MM-dd") + ")'");
				jsonChildBuffer.append(",'dataNo':'" + sendGoods.getSendGoodsNo() + "'");
				jsonChildBuffer.append("}");
			}
		}
		jsonChildBuffer.append("]");
		if (jsonChildBuffer.length() > 14) {jsonBuffer.append(jsonChildBuffer);}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}
	
	public List<InvoiceReg> findHaveFreight(String inventoryWay) {
		return invoiceRegDao.findHaveFreight(inventoryWay);
	}
	
	public List<InvoiceReg> findInvoiceAcceptance() {
		return invoiceRegDao.findInvoiceAcceptance();
	}
	
}
