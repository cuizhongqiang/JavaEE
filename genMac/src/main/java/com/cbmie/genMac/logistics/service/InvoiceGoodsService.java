package com.cbmie.genMac.logistics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.InvoiceGoodsDao;
import com.cbmie.genMac.logistics.entity.InvoiceGoods;
import com.cbmie.genMac.logistics.entity.InvoiceReg;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 到单商品service
 */
@Service
@Transactional
public class InvoiceGoodsService extends BaseService<InvoiceGoods, Long> {

	@Autowired
	private InvoiceGoodsDao invoiceGoodsDao;

	@Override
	public HibernateDao<InvoiceGoods, Long> getEntityDao() {
		return invoiceGoodsDao;
	}
	
	public void save(InvoiceReg invoiceReg, String invoiceGoodsJson, User currentUser) {
		// 转成标准的json字符串
		invoiceGoodsJson = StringEscapeUtils.unescapeHtml4(invoiceGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<InvoiceGoods> invoiceGoodsList = new ArrayList<InvoiceGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(invoiceGoodsJson);
			for (JsonNode jn : jsonNode) {
				InvoiceGoods invoiceGoods = objectMapper.readValue(jn.toString(), InvoiceGoods.class);
				invoiceGoodsList.add(invoiceGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取invoiceReg中invoiceGoods持久化对象
		List<InvoiceGoods> dataInvoiceGoodsList = invoiceReg.getInvoiceGoods();
		// 将数据库数据放入映射
		Map<Long, InvoiceGoods> dataInvoiceGoodsMap = new HashMap<Long, InvoiceGoods>(); 
		for (InvoiceGoods dataInvoiceGoods : dataInvoiceGoodsList) {
			dataInvoiceGoodsMap.put(dataInvoiceGoods.getId(), dataInvoiceGoods);
		}
		// 排除没有发生改变的
		for (InvoiceGoods dataInvoiceGoods : dataInvoiceGoodsList) {
			if (invoiceGoodsList.contains(dataInvoiceGoods)) {
				invoiceGoodsList.remove(dataInvoiceGoods);
				dataInvoiceGoodsMap.remove(dataInvoiceGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (InvoiceGoods invoiceGoods : invoiceGoodsList) {
			if (invoiceGoods.getId() == null) {
				insert(invoiceReg.getId(), invoiceGoods, dataInvoiceGoodsList, currentUser); // 新增 
			}
			InvoiceGoods dataInvoiceGoods = dataInvoiceGoodsMap.get(invoiceGoods.getId());
			if (dataInvoiceGoods != null) {
				update(dataInvoiceGoods, invoiceGoods, currentUser); // 修改
				dataInvoiceGoodsMap.remove(invoiceGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, InvoiceGoods> entry : dataInvoiceGoodsMap.entrySet()) {
			dataInvoiceGoodsList.remove(entry.getValue());
			invoiceGoodsDao.delete(entry.getKey());
		}
	}

	private void update(InvoiceGoods dataInvoiceGoods, InvoiceGoods invoiceGoods, User currentUser) {
		dataInvoiceGoods.setGoodsCategory(invoiceGoods.getGoodsCategory());
		dataInvoiceGoods.setSpecification(invoiceGoods.getSpecification());
		dataInvoiceGoods.setFrameNo(invoiceGoods.getFrameNo());
		dataInvoiceGoods.setAmount(invoiceGoods.getAmount());
		dataInvoiceGoods.setUnit(invoiceGoods.getUnit());
		dataInvoiceGoods.setPrice(invoiceGoods.getPrice());
		dataInvoiceGoods.setOriginal(invoiceGoods.getOriginal());
		dataInvoiceGoods.setTax(invoiceGoods.getTax());
		dataInvoiceGoods.setTaxRate(invoiceGoods.getTaxRate());
		dataInvoiceGoods.setSaleTax(invoiceGoods.getSaleTax());
		dataInvoiceGoods.setSaleTaxRate(invoiceGoods.getSaleTaxRate());
		dataInvoiceGoods.setVat(invoiceGoods.getVat());
		dataInvoiceGoods.setVatRate(invoiceGoods.getVatRate());
	}
	
	private void insert(Long pid, InvoiceGoods invoiceGoods, List<InvoiceGoods> dataInvoiceGoodsList, User currentUser) {
		invoiceGoods.setId(null);
		invoiceGoods.setPid(pid);
		dataInvoiceGoodsList.add(invoiceGoods);
	}
	
}
