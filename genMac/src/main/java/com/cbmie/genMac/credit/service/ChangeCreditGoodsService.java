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
import com.cbmie.genMac.credit.dao.ChangeCreditGoodsDao;
import com.cbmie.genMac.credit.entity.ChangeCredit;
import com.cbmie.genMac.credit.entity.ChangeCreditGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 改证商品service
 */
@Service
@Transactional
public class ChangeCreditGoodsService extends BaseService<ChangeCreditGoods, Long> {

	@Autowired
	private ChangeCreditGoodsDao changeCreditGoodsDao;

	@Override
	public HibernateDao<ChangeCreditGoods, Long> getEntityDao() {
		return changeCreditGoodsDao;
	}
	
	public void save(ChangeCredit changeCredit, String changeCreditGoodsJson) {
		// 转成标准的json字符串
		changeCreditGoodsJson = StringEscapeUtils.unescapeHtml4(changeCreditGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<ChangeCreditGoods> changeCreditGoodsList = new ArrayList<ChangeCreditGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(changeCreditGoodsJson);
			for (JsonNode jn : jsonNode) {
				ChangeCreditGoods changeCreditGoods = objectMapper.readValue(jn.toString(), ChangeCreditGoods.class);
				changeCreditGoodsList.add(changeCreditGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取changeCredit中changeCreditGoods持久化对象
		List<ChangeCreditGoods> dataChangeCreditGoodsList = changeCredit.getChangeCreditGoods();
		// 将数据库数据放入映射
		Map<Long, ChangeCreditGoods> dataChangeCreditGoodsMap = new HashMap<Long, ChangeCreditGoods>(); 
		for (ChangeCreditGoods dataChangeCreditGoods : dataChangeCreditGoodsList) {
			dataChangeCreditGoodsMap.put(dataChangeCreditGoods.getId(), dataChangeCreditGoods);
		}
		// 排除没有发生改变的
		for (ChangeCreditGoods dataChangeCreditGoods : dataChangeCreditGoodsList) {
			if (changeCreditGoodsList.contains(dataChangeCreditGoods)) {
				changeCreditGoodsList.remove(dataChangeCreditGoods);
				dataChangeCreditGoodsMap.remove(dataChangeCreditGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (ChangeCreditGoods changeCreditGoods : changeCreditGoodsList) {
			if (changeCreditGoods.getId() == null) {
				insert(changeCredit.getId(), changeCreditGoods, dataChangeCreditGoodsList); // 新增 
			}
			ChangeCreditGoods dataChangeCreditGoods = dataChangeCreditGoodsMap.get(changeCreditGoods.getId());
			if (dataChangeCreditGoods != null) {
				update(dataChangeCreditGoods, changeCreditGoods); // 修改
				dataChangeCreditGoodsMap.remove(changeCreditGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, ChangeCreditGoods> entry : dataChangeCreditGoodsMap.entrySet()) {
			dataChangeCreditGoodsList.remove(entry.getValue());
			changeCreditGoodsDao.delete(entry.getKey());
		}
	}

	private void update(ChangeCreditGoods dataChangeCreditGoods, ChangeCreditGoods changeCreditGoods) {
		dataChangeCreditGoods.setGoodsCategory(changeCreditGoods.getGoodsCategory());
		dataChangeCreditGoods.setSpecification(changeCreditGoods.getSpecification());
		dataChangeCreditGoods.setAmount(changeCreditGoods.getAmount());
		dataChangeCreditGoods.setUnit(changeCreditGoods.getUnit());
	}
	
	private void insert(Long pid, ChangeCreditGoods changeCreditGoods, List<ChangeCreditGoods> dataChangeCreditGoodsList) {
		changeCreditGoods.setId(null);
		changeCreditGoods.setPid(pid);
		dataChangeCreditGoodsList.add(changeCreditGoods);
	}
}
