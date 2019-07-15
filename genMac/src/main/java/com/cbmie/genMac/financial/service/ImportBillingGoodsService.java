package com.cbmie.genMac.financial.service;

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
import com.cbmie.genMac.financial.dao.ImportBillingGoodsDao;
import com.cbmie.genMac.financial.entity.ImportBilling;
import com.cbmie.genMac.financial.entity.ImportBillingGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 进口结算联系单商品service
 */
@Service
@Transactional
public class ImportBillingGoodsService extends BaseService<ImportBillingGoods, Long> {

	@Autowired
	private ImportBillingGoodsDao importBillingGoodsDao;

	@Override
	public HibernateDao<ImportBillingGoods, Long> getEntityDao() {
		return importBillingGoodsDao;
	}
	
	public void save(ImportBilling importBilling, String importBillingGoodsJson, User currentUser) {
		// 转成标准的json字符串
		importBillingGoodsJson = StringEscapeUtils.unescapeHtml4(importBillingGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<ImportBillingGoods> importBillingGoodsList = new ArrayList<ImportBillingGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(importBillingGoodsJson);
			for (JsonNode jn : jsonNode) {
				ImportBillingGoods importBillingGoods = objectMapper.readValue(jn.toString(), ImportBillingGoods.class);
				importBillingGoodsList.add(importBillingGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取importBilling中importBillingGoods持久化对象
		List<ImportBillingGoods> dataImportBillingGoodsList = importBilling.getImportBillingGoods();
		// 将数据库数据放入映射
		Map<Long, ImportBillingGoods> dataImportBillingGoodsMap = new HashMap<Long, ImportBillingGoods>(); 
		for (ImportBillingGoods dataImportBillingGoods : dataImportBillingGoodsList) {
			dataImportBillingGoodsMap.put(dataImportBillingGoods.getId(), dataImportBillingGoods);
		}
		// 排除没有发生改变的
		for (ImportBillingGoods dataImportBillingGoods : dataImportBillingGoodsList) {
			if (importBillingGoodsList.contains(dataImportBillingGoods)) {
				importBillingGoodsList.remove(dataImportBillingGoods);
				dataImportBillingGoodsMap.remove(dataImportBillingGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (ImportBillingGoods importBillingGoods : importBillingGoodsList) {
			if (importBillingGoods.getId() == null) {
				insert(importBilling.getId(), importBillingGoods, dataImportBillingGoodsList, currentUser); // 新增 
			}
			ImportBillingGoods dataImportBillingGoods = dataImportBillingGoodsMap.get(importBillingGoods.getId());
			if (dataImportBillingGoods != null) {
				update(dataImportBillingGoods, importBillingGoods, currentUser); // 修改
				dataImportBillingGoodsMap.remove(importBillingGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, ImportBillingGoods> entry : dataImportBillingGoodsMap.entrySet()) {
			dataImportBillingGoodsList.remove(entry.getValue());
			importBillingGoodsDao.delete(entry.getKey());
		}
	}

	private void update(ImportBillingGoods dataImportBillingGoods, ImportBillingGoods importBillingGoods, User currentUser) {
		dataImportBillingGoods.setGoodsCategory(importBillingGoods.getGoodsCategory());
		dataImportBillingGoods.setSpecification(importBillingGoods.getSpecification());
		dataImportBillingGoods.setUnit(importBillingGoods.getUnit());
		dataImportBillingGoods.setAmount(importBillingGoods.getAmount());
		dataImportBillingGoods.setPrice(importBillingGoods.getPrice());
		dataImportBillingGoods.setSalesAmount(importBillingGoods.getSalesAmount());
		dataImportBillingGoods.setRateMain(importBillingGoods.getRateMain());
		dataImportBillingGoods.setTaxMain(importBillingGoods.getTaxMain());
	}
	
	private void insert(Long pid, ImportBillingGoods importBillingGoods, List<ImportBillingGoods> dataImportBillingGoodsList, User currentUser) {
		importBillingGoods.setPid(pid);
		dataImportBillingGoodsList.add(importBillingGoods);
	}
	
}
