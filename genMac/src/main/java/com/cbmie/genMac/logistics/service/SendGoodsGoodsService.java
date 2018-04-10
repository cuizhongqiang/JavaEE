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
import com.cbmie.genMac.logistics.dao.SendGoodsGoodsDao;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.entity.SendGoodsGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 放货商品service
 */
@Service
@Transactional
public class SendGoodsGoodsService extends BaseService<SendGoodsGoods, Long> {

	@Autowired
	private SendGoodsGoodsDao sendGoodsGoodsDao;

	@Override
	public HibernateDao<SendGoodsGoods, Long> getEntityDao() {
		return sendGoodsGoodsDao;
	}
	
	public void save(SendGoods sendGoods, String sendGoodsGoodsJson, User currentUser) {
		// 转成标准的json字符串
		sendGoodsGoodsJson = StringEscapeUtils.unescapeHtml4(sendGoodsGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SendGoodsGoods> sendGoodsGoodsList = new ArrayList<SendGoodsGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(sendGoodsGoodsJson);
			for (JsonNode jn : jsonNode) {
				SendGoodsGoods sendGoodsGoods = objectMapper.readValue(jn.toString(), SendGoodsGoods.class);
				sendGoodsGoodsList.add(sendGoodsGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取sendGoods中sendGoodsGoods持久化对象
		List<SendGoodsGoods> dataSendGoodsGoodsList = sendGoods.getSendGoodsGoods();
		// 将数据库数据放入映射
		Map<Long, SendGoodsGoods> dataSendGoodsGoodsMap = new HashMap<Long, SendGoodsGoods>(); 
		for (SendGoodsGoods dataSendGoodsGoods : dataSendGoodsGoodsList) {
			dataSendGoodsGoodsMap.put(dataSendGoodsGoods.getId(), dataSendGoodsGoods);
		}
		// 排除没有发生改变的
		for (SendGoodsGoods dataSendGoodsGoods : dataSendGoodsGoodsList) {
			if (sendGoodsGoodsList.contains(dataSendGoodsGoods)) {
				sendGoodsGoodsList.remove(dataSendGoodsGoods);
				dataSendGoodsGoodsMap.remove(dataSendGoodsGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (SendGoodsGoods sendGoodsGoods : sendGoodsGoodsList) {
			if (sendGoodsGoods.getId() == null) {
				insert(sendGoods.getId(), sendGoodsGoods, dataSendGoodsGoodsList, currentUser); // 新增 
			}
			SendGoodsGoods dataSendGoodsGoods = dataSendGoodsGoodsMap.get(sendGoodsGoods.getId());
			if (dataSendGoodsGoods != null) {
				update(dataSendGoodsGoods, sendGoodsGoods, currentUser); // 修改
				dataSendGoodsGoodsMap.remove(sendGoodsGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, SendGoodsGoods> entry : dataSendGoodsGoodsMap.entrySet()) {
			dataSendGoodsGoodsList.remove(entry.getValue());
			sendGoodsGoodsDao.delete(entry.getKey());
		}
	}

	private void update(SendGoodsGoods dataSendGoodsGoods, SendGoodsGoods sendGoodsGoods, User currentUser) {
		dataSendGoodsGoods.setGoodsCategory(sendGoodsGoods.getGoodsCategory());
		dataSendGoodsGoods.setSpecification(sendGoodsGoods.getSpecification());
		dataSendGoodsGoods.setFrameNo(sendGoodsGoods.getFrameNo());
		dataSendGoodsGoods.setAmount(sendGoodsGoods.getAmount());
		dataSendGoodsGoods.setUnit(sendGoodsGoods.getUnit());
		dataSendGoodsGoods.setPrice(sendGoodsGoods.getPrice());
		dataSendGoodsGoods.setSalesAmount(sendGoodsGoods.getSalesAmount());
	}
	
	private void insert(Long pid, SendGoodsGoods sendGoodsGoods, List<SendGoodsGoods> dataSendGoodsGoodsList, User currentUser) {
		sendGoodsGoods.setId(null);
		sendGoodsGoods.setParentId(pid);
		dataSendGoodsGoodsList.add(sendGoodsGoods);
	}
	
}
