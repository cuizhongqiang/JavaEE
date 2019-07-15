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
import com.cbmie.genMac.domesticTrade.dao.AgentPurchaseGoodsDao;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchaseGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同service
 */
@Service
@Transactional(readOnly = true)
public class AgentPurchaseGoodsService extends BaseService<AgentPurchaseGoods, Long> {

	@Autowired
	private AgentPurchaseGoodsDao agentPurchaseGoodsDao;

	@Override
	public HibernateDao<AgentPurchaseGoods, Long> getEntityDao() {
		return agentPurchaseGoodsDao;
	}
	
	public void save(AgentPurchase agentPurchase, String AgentPurchaseGoodsJson, User currentUser) {
		// 转成标准的json字符串
		AgentPurchaseGoodsJson = StringEscapeUtils.unescapeHtml4(AgentPurchaseGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AgentPurchaseGoods> agentPurchaseGoodsList = new ArrayList<AgentPurchaseGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(AgentPurchaseGoodsJson);
			for (JsonNode jn : jsonNode) {
				AgentPurchaseGoods agentPurchaseGoods = objectMapper.readValue(jn.toString(), AgentPurchaseGoods.class);
				agentPurchaseGoodsList.add(agentPurchaseGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取AgentPurchase中AgentPurchaseGoods持久化对象
		List<AgentPurchaseGoods> dataAgentPurchaseGoodsList = agentPurchase.getAgentPurchaseGoods();
		// 将数据库数据放入映射
		Map<Long, AgentPurchaseGoods> dataAgentPurchaseGoodsMap = new HashMap<Long, AgentPurchaseGoods>(); 
		for (AgentPurchaseGoods dataAgentPurchaseGoods : dataAgentPurchaseGoodsList) {
			dataAgentPurchaseGoodsMap.put(dataAgentPurchaseGoods.getId(), dataAgentPurchaseGoods);
		}
		// 排除没有发生改变的
		for (AgentPurchaseGoods dataAgentPurchaseGoods : dataAgentPurchaseGoodsList) {
			if (agentPurchaseGoodsList.contains(dataAgentPurchaseGoods)) {
				agentPurchaseGoodsList.remove(dataAgentPurchaseGoods);
				dataAgentPurchaseGoodsMap.remove(dataAgentPurchaseGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AgentPurchaseGoods agentPurchaseGoods : agentPurchaseGoodsList) {
			if (agentPurchaseGoods.getId() == null) {
				insert(agentPurchase.getId(), agentPurchaseGoods, dataAgentPurchaseGoodsList, currentUser); // 新增 
			}
			AgentPurchaseGoods dataAgentPurchaseGoods = dataAgentPurchaseGoodsMap.get(agentPurchaseGoods.getId());
			if (dataAgentPurchaseGoods != null) {
				update(dataAgentPurchaseGoods, agentPurchaseGoods, currentUser); // 修改
				dataAgentPurchaseGoodsMap.remove(agentPurchaseGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AgentPurchaseGoods> entry : dataAgentPurchaseGoodsMap.entrySet()) {
			dataAgentPurchaseGoodsList.remove(entry.getValue());
			agentPurchaseGoodsDao.delete(entry.getKey());
		}
	}

	private void update(AgentPurchaseGoods dataAgentPurchaseGoods, AgentPurchaseGoods agentPurchaseGoods, User currentUser) {
		dataAgentPurchaseGoods.setGoodsCategory(agentPurchaseGoods.getGoodsCategory());
		dataAgentPurchaseGoods.setGoodsCode(agentPurchaseGoods.getGoodsCode());
		dataAgentPurchaseGoods.setSpecification(agentPurchaseGoods.getSpecification());
		dataAgentPurchaseGoods.setAmount(agentPurchaseGoods.getAmount());
		dataAgentPurchaseGoods.setUnit(agentPurchaseGoods.getUnit());
		dataAgentPurchaseGoods.setPrice(agentPurchaseGoods.getPrice());
	}
	
	private void insert(Long pid, AgentPurchaseGoods agentPurchaseGoods, List<AgentPurchaseGoods> dataAgentPurchaseGoodsList, User currentUser) {
		agentPurchaseGoods.setPid(pid);
		dataAgentPurchaseGoodsList.add(agentPurchaseGoods);
	}
}
