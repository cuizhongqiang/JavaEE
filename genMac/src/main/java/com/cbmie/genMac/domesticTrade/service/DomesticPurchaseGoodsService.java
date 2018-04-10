package com.cbmie.genMac.domesticTrade.service;

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
import com.cbmie.genMac.domesticTrade.dao.DomesticPurchaseGoodsDao;
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchase;
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchaseGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同service
 */
@Service
@Transactional(readOnly = true)
public class DomesticPurchaseGoodsService extends BaseService<DomesticPurchaseGoods, Long> {

	@Autowired
	private DomesticPurchaseGoodsDao domesticPurchaseGoodsDao;

	@Override
	public HibernateDao<DomesticPurchaseGoods, Long> getEntityDao() {
		return domesticPurchaseGoodsDao;
	}
	
	public void save(DomesticPurchase domesticPurchase, String DomesticPurchaseGoodsJson, User currentUser) {
		// 转成标准的json字符串
		DomesticPurchaseGoodsJson = StringEscapeUtils.unescapeHtml4(DomesticPurchaseGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<DomesticPurchaseGoods> domesticPurchaseGoodsList = new ArrayList<DomesticPurchaseGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(DomesticPurchaseGoodsJson);
			for (JsonNode jn : jsonNode) {
				DomesticPurchaseGoods domesticPurchaseGoods = objectMapper.readValue(jn.toString(), DomesticPurchaseGoods.class);
				domesticPurchaseGoodsList.add(domesticPurchaseGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取DomesticPurchase中DomesticPurchaseGoods持久化对象
		List<DomesticPurchaseGoods> dataDomesticPurchaseGoodsList = domesticPurchase.getDomesticPurchaseGoods();
		// 将数据库数据放入映射
		Map<Long, DomesticPurchaseGoods> dataDomesticPurchaseGoodsMap = new HashMap<Long, DomesticPurchaseGoods>(); 
		for (DomesticPurchaseGoods dataDomesticPurchaseGoods : dataDomesticPurchaseGoodsList) {
			dataDomesticPurchaseGoodsMap.put(dataDomesticPurchaseGoods.getId(), dataDomesticPurchaseGoods);
		}
		// 排除没有发生改变的
		for (DomesticPurchaseGoods dataDomesticPurchaseGoods : dataDomesticPurchaseGoodsList) {
			if (domesticPurchaseGoodsList.contains(dataDomesticPurchaseGoods)) {
				domesticPurchaseGoodsList.remove(dataDomesticPurchaseGoods);
				dataDomesticPurchaseGoodsMap.remove(dataDomesticPurchaseGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (DomesticPurchaseGoods domesticPurchaseGoods : domesticPurchaseGoodsList) {
			if (domesticPurchaseGoods.getId() == null) {
				insert(domesticPurchase.getId(), domesticPurchaseGoods, dataDomesticPurchaseGoodsList, currentUser); // 新增 
			}
			DomesticPurchaseGoods dataDomesticPurchaseGoods = dataDomesticPurchaseGoodsMap.get(domesticPurchaseGoods.getId());
			if (dataDomesticPurchaseGoods != null) {
				update(dataDomesticPurchaseGoods, domesticPurchaseGoods, currentUser); // 修改
				dataDomesticPurchaseGoodsMap.remove(domesticPurchaseGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, DomesticPurchaseGoods> entry : dataDomesticPurchaseGoodsMap.entrySet()) {
			dataDomesticPurchaseGoodsList.remove(entry.getValue());
			domesticPurchaseGoodsDao.delete(entry.getKey());
		}
	}

	private void update(DomesticPurchaseGoods dataDomesticPurchaseGoods, DomesticPurchaseGoods domesticPurchaseGoods, User currentUser) {
		dataDomesticPurchaseGoods.setGoodsCategory(domesticPurchaseGoods.getGoodsCategory());
		dataDomesticPurchaseGoods.setGoodsCode(domesticPurchaseGoods.getGoodsCode());
		dataDomesticPurchaseGoods.setSpecification(domesticPurchaseGoods.getSpecification());
		dataDomesticPurchaseGoods.setAmount(domesticPurchaseGoods.getAmount());
		dataDomesticPurchaseGoods.setUnit(domesticPurchaseGoods.getUnit());
		dataDomesticPurchaseGoods.setPrice(domesticPurchaseGoods.getPrice());
	}
	
	private void insert(Long pid, DomesticPurchaseGoods domesticPurchaseGoods, List<DomesticPurchaseGoods> dataDomesticPurchaseGoodsList, User currentUser) {
		domesticPurchaseGoods.setPid(pid);
		dataDomesticPurchaseGoodsList.add(domesticPurchaseGoods);
	}
}
