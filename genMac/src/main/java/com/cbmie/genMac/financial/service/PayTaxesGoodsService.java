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
import com.cbmie.genMac.financial.dao.PayTaxesGoodsDao;
import com.cbmie.genMac.financial.entity.PayTaxes;
import com.cbmie.genMac.financial.entity.PayTaxesGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 交税商品service
 */
@Service
@Transactional
public class PayTaxesGoodsService extends BaseService<PayTaxesGoods, Long> {

	@Autowired
	private PayTaxesGoodsDao payTaxesGoodsDao;

	@Override
	public HibernateDao<PayTaxesGoods, Long> getEntityDao() {
		return payTaxesGoodsDao;
	}
	
	public void save(PayTaxes payTaxes, String payTaxesGoodsJson) {
		// 转成标准的json字符串
		payTaxesGoodsJson = StringEscapeUtils.unescapeHtml4(payTaxesGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<PayTaxesGoods> payTaxesGoodsList = new ArrayList<PayTaxesGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(payTaxesGoodsJson);
			for (JsonNode jn : jsonNode) {
				PayTaxesGoods payTaxesGoods = objectMapper.readValue(jn.toString(), PayTaxesGoods.class);
				payTaxesGoodsList.add(payTaxesGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取payTaxes中payTaxesGoods持久化对象
		List<PayTaxesGoods> dataPayTaxesGoodsList = payTaxes.getPayTaxesGoods();
		// 将数据库数据放入映射
		Map<Long, PayTaxesGoods> dataPayTaxesGoodsMap = new HashMap<Long, PayTaxesGoods>(); 
		for (PayTaxesGoods dataPayTaxesGoods : dataPayTaxesGoodsList) {
			dataPayTaxesGoodsMap.put(dataPayTaxesGoods.getId(), dataPayTaxesGoods);
		}
		// 排除没有发生改变的
		for (PayTaxesGoods dataPayTaxesGoods : dataPayTaxesGoodsList) {
			if (payTaxesGoodsList.contains(dataPayTaxesGoods)) {
				payTaxesGoodsList.remove(dataPayTaxesGoods);
				dataPayTaxesGoodsMap.remove(dataPayTaxesGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (PayTaxesGoods payTaxesGoods : payTaxesGoodsList) {
			if (payTaxesGoods.getId() == null) {
				insert(payTaxes.getId(), payTaxesGoods, dataPayTaxesGoodsList); // 新增 
			}
			PayTaxesGoods dataPayTaxesGoods = dataPayTaxesGoodsMap.get(payTaxesGoods.getId());
			if (dataPayTaxesGoods != null) {
				update(dataPayTaxesGoods, payTaxesGoods); // 修改
				dataPayTaxesGoodsMap.remove(payTaxesGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, PayTaxesGoods> entry : dataPayTaxesGoodsMap.entrySet()) {
			dataPayTaxesGoodsList.remove(entry.getValue());
			payTaxesGoodsDao.delete(entry.getKey());
		}
	}

	private void update(PayTaxesGoods dataPayTaxesGoods, PayTaxesGoods payTaxesGoods) {
		dataPayTaxesGoods.setGoodsCategory(payTaxesGoods.getGoodsCategory());
		dataPayTaxesGoods.setSpecification(payTaxesGoods.getSpecification());
		dataPayTaxesGoods.setUnit(payTaxesGoods.getUnit());
		dataPayTaxesGoods.setAmount(payTaxesGoods.getAmount());
	}
	
	private void insert(Long pid, PayTaxesGoods payTaxesGoods, List<PayTaxesGoods> dataPayTaxesGoodsList) {
		payTaxesGoods.setPid(pid);
		dataPayTaxesGoodsList.add(payTaxesGoods);
	}
	
}
