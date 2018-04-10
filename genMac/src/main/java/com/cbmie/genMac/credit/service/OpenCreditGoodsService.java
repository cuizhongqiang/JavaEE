package com.cbmie.genMac.credit.service;

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
import com.cbmie.genMac.credit.dao.OpenCreditGoodsDao;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.entity.OpenCreditGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 开证商品service
 */
@Service
@Transactional
public class OpenCreditGoodsService extends BaseService<OpenCreditGoods, Long> {

	@Autowired
	private OpenCreditGoodsDao openCreditGoodsDao;

	@Override
	public HibernateDao<OpenCreditGoods, Long> getEntityDao() {
		return openCreditGoodsDao;
	}
	
	public void save(OpenCredit openCredit, String openCreditGoodsJson) {
		// 转成标准的json字符串
		openCreditGoodsJson = StringEscapeUtils.unescapeHtml4(openCreditGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<OpenCreditGoods> openCreditGoodsList = new ArrayList<OpenCreditGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(openCreditGoodsJson);
			for (JsonNode jn : jsonNode) {
				OpenCreditGoods openCreditGoods = objectMapper.readValue(jn.toString(), OpenCreditGoods.class);
				openCreditGoodsList.add(openCreditGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取openCredit中openCreditGoods持久化对象
		List<OpenCreditGoods> dataOpenCreditGoodsList = openCredit.getOpenCreditGoods();
		// 将数据库数据放入映射
		Map<Long, OpenCreditGoods> dataOpenCreditGoodsMap = new HashMap<Long, OpenCreditGoods>(); 
		for (OpenCreditGoods dataOpenCreditGoods : dataOpenCreditGoodsList) {
			dataOpenCreditGoodsMap.put(dataOpenCreditGoods.getId(), dataOpenCreditGoods);
		}
		// 排除没有发生改变的
		for (OpenCreditGoods dataOpenCreditGoods : dataOpenCreditGoodsList) {
			if (openCreditGoodsList.contains(dataOpenCreditGoods)) {
				openCreditGoodsList.remove(dataOpenCreditGoods);
				dataOpenCreditGoodsMap.remove(dataOpenCreditGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (OpenCreditGoods openCreditGoods : openCreditGoodsList) {
			if (openCreditGoods.getId() == null) {
				insert(openCredit.getId(), openCreditGoods, dataOpenCreditGoodsList); // 新增 
			}
			OpenCreditGoods dataOpenCreditGoods = dataOpenCreditGoodsMap.get(openCreditGoods.getId());
			if (dataOpenCreditGoods != null) {
				update(dataOpenCreditGoods, openCreditGoods); // 修改
				dataOpenCreditGoodsMap.remove(openCreditGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, OpenCreditGoods> entry : dataOpenCreditGoodsMap.entrySet()) {
			dataOpenCreditGoodsList.remove(entry.getValue());
			openCreditGoodsDao.delete(entry.getKey());
		}
	}

	private void update(OpenCreditGoods dataOpenCreditGoods, OpenCreditGoods openCreditGoods) {
		dataOpenCreditGoods.setGoodsCategory(openCreditGoods.getGoodsCategory());
		dataOpenCreditGoods.setSpecification(openCreditGoods.getSpecification());
		dataOpenCreditGoods.setAmount(openCreditGoods.getAmount());
		dataOpenCreditGoods.setUnit(openCreditGoods.getUnit());
	}
	
	private void insert(Long pid, OpenCreditGoods openCreditGoods, List<OpenCreditGoods> dataOpenCreditGoodsList) {
		openCreditGoods.setId(null);
		openCreditGoods.setPid(pid);
		dataOpenCreditGoodsList.add(openCreditGoods);
	}
}
