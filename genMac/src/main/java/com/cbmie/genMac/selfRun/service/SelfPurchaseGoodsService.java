package com.cbmie.genMac.selfRun.service;

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
import com.cbmie.genMac.selfRun.dao.SelfPurchaseGoodsDao;
import com.cbmie.genMac.selfRun.entity.SelfPurchase;
import com.cbmie.genMac.selfRun.entity.SelfPurchaseGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同商品service
 */
@Service
@Transactional
public class SelfPurchaseGoodsService extends BaseService<SelfPurchaseGoods, Long> {

	@Autowired
	private SelfPurchaseGoodsDao selfPurchaseGoodsDao;

	@Override
	public HibernateDao<SelfPurchaseGoods, Long> getEntityDao() {
		return selfPurchaseGoodsDao;
	}
	
	public void save(SelfPurchase selfPurchase, String SelfPurchaseGoodsJson, User currentUser) {
		// 转成标准的json字符串
		SelfPurchaseGoodsJson = StringEscapeUtils.unescapeHtml4(SelfPurchaseGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SelfPurchaseGoods> selfPurchaseGoodsList = new ArrayList<SelfPurchaseGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(SelfPurchaseGoodsJson);
			for (JsonNode jn : jsonNode) {
				SelfPurchaseGoods selfPurchaseGoods = objectMapper.readValue(jn.toString(), SelfPurchaseGoods.class);
				selfPurchaseGoodsList.add(selfPurchaseGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取SelfPurchase中SelfPurchaseGoods持久化对象
		List<SelfPurchaseGoods> dataSelfPurchaseGoodsList = selfPurchase.getSelfPurchaseGoods();
		// 将数据库数据放入映射
		Map<Long, SelfPurchaseGoods> dataSelfPurchaseGoodsMap = new HashMap<Long, SelfPurchaseGoods>(); 
		for (SelfPurchaseGoods dataSelfPurchaseGoods : dataSelfPurchaseGoodsList) {
			dataSelfPurchaseGoodsMap.put(dataSelfPurchaseGoods.getId(), dataSelfPurchaseGoods);
		}
		// 排除没有发生改变的
		for (SelfPurchaseGoods dataSelfPurchaseGoods : dataSelfPurchaseGoodsList) {
			if (selfPurchaseGoodsList.contains(dataSelfPurchaseGoods)) {
				selfPurchaseGoodsList.remove(dataSelfPurchaseGoods);
				dataSelfPurchaseGoodsMap.remove(dataSelfPurchaseGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (SelfPurchaseGoods selfPurchaseGoods : selfPurchaseGoodsList) {
			if (selfPurchaseGoods.getId() == null) {
				insert(selfPurchase.getId(), selfPurchaseGoods, dataSelfPurchaseGoodsList, currentUser); // 新增 
			}
			SelfPurchaseGoods dataSelfPurchaseGoods = dataSelfPurchaseGoodsMap.get(selfPurchaseGoods.getId());
			if (dataSelfPurchaseGoods != null) {
				update(dataSelfPurchaseGoods, selfPurchaseGoods, currentUser); // 修改
				dataSelfPurchaseGoodsMap.remove(selfPurchaseGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, SelfPurchaseGoods> entry : dataSelfPurchaseGoodsMap.entrySet()) {
			dataSelfPurchaseGoodsList.remove(entry.getValue());
			selfPurchaseGoodsDao.delete(entry.getKey());
		}
	}

	private void update(SelfPurchaseGoods dataSelfPurchaseGoods, SelfPurchaseGoods selfPurchaseGoods, User currentUser) {
		dataSelfPurchaseGoods.setGoodsCategory(selfPurchaseGoods.getGoodsCategory());
		dataSelfPurchaseGoods.setGoodsCode(selfPurchaseGoods.getGoodsCode());
		dataSelfPurchaseGoods.setSpecification(selfPurchaseGoods.getSpecification());
		dataSelfPurchaseGoods.setAmount(selfPurchaseGoods.getAmount());
		dataSelfPurchaseGoods.setUnit(selfPurchaseGoods.getUnit());
		dataSelfPurchaseGoods.setPrice(selfPurchaseGoods.getPrice());
	}
	
	private void insert(Long pid, SelfPurchaseGoods selfPurchaseGoods, List<SelfPurchaseGoods> dataSelfPurchaseGoodsList, User currentUser) {
		selfPurchaseGoods.setPid(pid);
		dataSelfPurchaseGoodsList.add(selfPurchaseGoods);
	}
	
}
