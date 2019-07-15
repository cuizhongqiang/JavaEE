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
import com.cbmie.genMac.logistics.dao.FreightGoodsDao;
import com.cbmie.genMac.logistics.entity.FreightGoods;
import com.cbmie.genMac.logistics.entity.Freight;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 货代商品service
 */
@Service
@Transactional
public class FreightGoodsService extends BaseService<FreightGoods, Long> {

	@Autowired
	private FreightGoodsDao freightGoodsDao;

	@Override
	public HibernateDao<FreightGoods, Long> getEntityDao() {
		return freightGoodsDao;
	}
	
	public void save(Freight freightReg, String freightGoodsJson, User currentUser) {
		// 转成标准的json字符串
		freightGoodsJson = StringEscapeUtils.unescapeHtml4(freightGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<FreightGoods> freightGoodsList = new ArrayList<FreightGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(freightGoodsJson);
			for (JsonNode jn : jsonNode) {
				FreightGoods freightGoods = objectMapper.readValue(jn.toString(), FreightGoods.class);
				freightGoodsList.add(freightGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取freightReg中freightGoods持久化对象
		List<FreightGoods> dataFreightGoodsList = freightReg.getFreightGoods();
		// 将数据库数据放入映射
		Map<Long, FreightGoods> dataFreightGoodsMap = new HashMap<Long, FreightGoods>(); 
		for (FreightGoods dataFreightGoods : dataFreightGoodsList) {
			dataFreightGoodsMap.put(dataFreightGoods.getId(), dataFreightGoods);
		}
		// 排除没有发生改变的
		for (FreightGoods dataFreightGoods : dataFreightGoodsList) {
			if (freightGoodsList.contains(dataFreightGoods)) {
				freightGoodsList.remove(dataFreightGoods);
				dataFreightGoodsMap.remove(dataFreightGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (FreightGoods freightGoods : freightGoodsList) {
			if (freightGoods.getId() == null) {
				insert(freightReg.getId(), freightGoods, dataFreightGoodsList, currentUser); // 新增 
			}
			FreightGoods dataFreightGoods = dataFreightGoodsMap.get(freightGoods.getId());
			if (dataFreightGoods != null) {
				update(dataFreightGoods, freightGoods, currentUser); // 修改
				dataFreightGoodsMap.remove(freightGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, FreightGoods> entry : dataFreightGoodsMap.entrySet()) {
			dataFreightGoodsList.remove(entry.getValue());
			freightGoodsDao.delete(entry.getKey());
		}
	}

	private void update(FreightGoods dataFreightGoods, FreightGoods freightGoods, User currentUser) {
		dataFreightGoods.setGoodsCategory(freightGoods.getGoodsCategory());
		dataFreightGoods.setSpecification(freightGoods.getSpecification());
		dataFreightGoods.setAmount(freightGoods.getAmount());
		dataFreightGoods.setChest(freightGoods.getChest());
		dataFreightGoods.setChestType(freightGoods.getChestType());
		dataFreightGoods.setStray(freightGoods.getStray());
		dataFreightGoods.setRemark(freightGoods.getRemark());
	}
	
	private void insert(Long pid, FreightGoods freightGoods, List<FreightGoods> dataFreightGoodsList, User currentUser) {
		freightGoods.setParentId(pid);
		dataFreightGoodsList.add(freightGoods);
	}
	
}
