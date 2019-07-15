package com.cbmie.genMac.agent.service;

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
import com.cbmie.genMac.agent.dao.AgentImportGoodsDao;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.agent.entity.AgentImportGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 代理进口商品service
 */
@Service
@Transactional
public class AgentImportGoodsService extends BaseService<AgentImportGoods, Long> {

	@Autowired
	private AgentImportGoodsDao agentImportGoodsDao;

	@Override
	public HibernateDao<AgentImportGoods, Long> getEntityDao() {
		return agentImportGoodsDao;
	}
	
	public void save(AgentImport agentImport, String agentImportGoodsJson, User currentUser) {
		// 转成标准的json字符串
		agentImportGoodsJson = StringEscapeUtils.unescapeHtml4(agentImportGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AgentImportGoods> agreementGoodsList = new ArrayList<AgentImportGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(agentImportGoodsJson);
			for (JsonNode jn : jsonNode) {
				AgentImportGoods agreementGoods = objectMapper.readValue(jn.toString(), AgentImportGoods.class);
				agreementGoodsList.add(agreementGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取agentImport中agreementGoods持久化对象
		List<AgentImportGoods> dataAgentImportGoodsList = agentImport.getAgentImportGoods();
		// 将数据库数据放入映射
		Map<Long, AgentImportGoods> dataAgentImportGoodsMap = new HashMap<Long, AgentImportGoods>(); 
		for (AgentImportGoods dataAgentImportGoods : dataAgentImportGoodsList) {
			dataAgentImportGoodsMap.put(dataAgentImportGoods.getId(), dataAgentImportGoods);
		}
		// 排除没有发生改变的
		for (AgentImportGoods dataAgentImportGoods : dataAgentImportGoodsList) {
			if (agreementGoodsList.contains(dataAgentImportGoods)) {
				agreementGoodsList.remove(dataAgentImportGoods);
				dataAgentImportGoodsMap.remove(dataAgentImportGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AgentImportGoods agreementGoods : agreementGoodsList) {
			if (agreementGoods.getId() == null) {
				insert(agentImport.getId(), agreementGoods, dataAgentImportGoodsList, currentUser); // 新增 
			}
			AgentImportGoods dataAgentImportGoods = dataAgentImportGoodsMap.get(agreementGoods.getId());
			if (dataAgentImportGoods != null) {
				update(dataAgentImportGoods, agreementGoods, currentUser); // 修改
				dataAgentImportGoodsMap.remove(agreementGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AgentImportGoods> entry : dataAgentImportGoodsMap.entrySet()) {
			dataAgentImportGoodsList.remove(entry.getValue());
			agentImportGoodsDao.delete(entry.getKey());
		}
	}

	private void update(AgentImportGoods dataAgentImportGoods, AgentImportGoods agreementGoods, User currentUser) {
		dataAgentImportGoods.setGoodsCategory(agreementGoods.getGoodsCategory());
		dataAgentImportGoods.setGoodsCode(agreementGoods.getGoodsCode());
		dataAgentImportGoods.setSpecification(agreementGoods.getSpecification());
		dataAgentImportGoods.setAmount(agreementGoods.getAmount());
		dataAgentImportGoods.setUnit(agreementGoods.getUnit());
		dataAgentImportGoods.setPrice(agreementGoods.getPrice());
	}
	
	private void insert(Long pid, AgentImportGoods agreementGoods, List<AgentImportGoods> dataAgentImportGoodsList, User currentUser) {
		agreementGoods.setPid(pid);
		dataAgentImportGoodsList.add(agreementGoods);
	}
	
}
