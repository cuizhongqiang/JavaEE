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
import com.cbmie.genMac.selfRun.dao.SelfSalesGoodsDao;
import com.cbmie.genMac.selfRun.entity.SelfSales;
import com.cbmie.genMac.selfRun.entity.SelfSalesGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售合同商品service
 */
@Service
@Transactional
public class SelfSalesGoodsService extends BaseService<SelfSalesGoods, Long> {

	@Autowired
	private SelfSalesGoodsDao selfSalesGoodsDao;

	@Override
	public HibernateDao<SelfSalesGoods, Long> getEntityDao() {
		return selfSalesGoodsDao;
	}
	
	public void save(SelfSales selfSales, String SelfSalesGoodsJson, User currentUser) {
		// 转成标准的json字符串
		SelfSalesGoodsJson = StringEscapeUtils.unescapeHtml4(SelfSalesGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SelfSalesGoods> selfSalesGoodsList = new ArrayList<SelfSalesGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(SelfSalesGoodsJson);
			for (JsonNode jn : jsonNode) {
				SelfSalesGoods selfSalesGoods = objectMapper.readValue(jn.toString(), SelfSalesGoods.class);
				selfSalesGoodsList.add(selfSalesGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取SelfSales中SelfSalesGoods持久化对象
		List<SelfSalesGoods> dataSelfSalesGoodsList = selfSales.getSelfSalesGoods();
		// 将数据库数据放入映射
		Map<Long, SelfSalesGoods> dataSelfSalesGoodsMap = new HashMap<Long, SelfSalesGoods>(); 
		for (SelfSalesGoods dataSelfSalesGoods : dataSelfSalesGoodsList) {
			dataSelfSalesGoodsMap.put(dataSelfSalesGoods.getId(), dataSelfSalesGoods);
		}
		// 排除没有发生改变的
		for (SelfSalesGoods dataSelfSalesGoods : dataSelfSalesGoodsList) {
			if (selfSalesGoodsList.contains(dataSelfSalesGoods)) {
				selfSalesGoodsList.remove(dataSelfSalesGoods);
				dataSelfSalesGoodsMap.remove(dataSelfSalesGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (SelfSalesGoods selfSalesGoods : selfSalesGoodsList) {
			if (selfSalesGoods.getId() == null) {
				insert(selfSales.getId(), selfSalesGoods, dataSelfSalesGoodsList, currentUser); // 新增 
			}
			SelfSalesGoods dataSelfSalesGoods = dataSelfSalesGoodsMap.get(selfSalesGoods.getId());
			if (dataSelfSalesGoods != null) {
				update(dataSelfSalesGoods, selfSalesGoods, currentUser); // 修改
				dataSelfSalesGoodsMap.remove(selfSalesGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, SelfSalesGoods> entry : dataSelfSalesGoodsMap.entrySet()) {
			dataSelfSalesGoodsList.remove(entry.getValue());
			selfSalesGoodsDao.delete(entry.getKey());
		}
	}

	private void update(SelfSalesGoods dataSelfSalesGoods, SelfSalesGoods selfSalesGoods, User currentUser) {
		dataSelfSalesGoods.setGoodsCategory(selfSalesGoods.getGoodsCategory());
		dataSelfSalesGoods.setGoodsCode(selfSalesGoods.getGoodsCode());
		dataSelfSalesGoods.setSpecification(selfSalesGoods.getSpecification());
		dataSelfSalesGoods.setAmount(selfSalesGoods.getAmount());
		dataSelfSalesGoods.setUnit(selfSalesGoods.getUnit());
		dataSelfSalesGoods.setPrice(selfSalesGoods.getPrice());
	}
	
	private void insert(Long pid, SelfSalesGoods selfSalesGoods, List<SelfSalesGoods> dataSelfSalesGoodsList, User currentUser) {
		selfSalesGoods.setPid(pid);
		dataSelfSalesGoodsList.add(selfSalesGoods);
	}
	
}
