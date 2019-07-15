package com.cbmie.genMac.stock.service;

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
import com.cbmie.genMac.stock.dao.InStockGoodsDao;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.stock.entity.InStockGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * service
 */
@Service
@Transactional
public class InStockGoodsService extends BaseService<InStockGoods, Long> {

	@Autowired
	private InStockGoodsDao inStockGoodsDao;

	@Override
	public HibernateDao<InStockGoods, Long> getEntityDao() {
		return inStockGoodsDao;
	}
	
	public void save(InStock inStock, String inStockGoodsJson, User currentUser) {
		try {
			// 转成标准的json字符串
			inStockGoodsJson = StringEscapeUtils.unescapeHtml4(inStockGoodsJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<InStockGoods> inStockGoodsList = new ArrayList<InStockGoods>();
			try {
				JsonNode jsonNode = objectMapper.readTree(inStockGoodsJson);
				for (JsonNode jn : jsonNode) {
					InStockGoods inStockGoods = objectMapper.readValue(jn.toString(), InStockGoods.class);
					inStockGoodsList.add(inStockGoods);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取invoiceReg中invoiceGoods持久化对象
			List<InStockGoods> dataInStockGoodsList = inStock.getInStockGoods();
			// 将数据库数据放入映射
			Map<Long, InStockGoods> dataInStockGoodsMap = new HashMap<Long, InStockGoods>(); 
			for (InStockGoods dataInStockGoods : dataInStockGoodsList) {
				dataInStockGoodsMap.put(dataInStockGoods.getId(), dataInStockGoods);
			}
			// 排除没有发生改变的
			for (InStockGoods dataInStockGoods : dataInStockGoodsList) {
				if (inStockGoodsList.contains(dataInStockGoods)) {
					inStockGoodsList.remove(dataInStockGoods);
					dataInStockGoodsMap.remove(dataInStockGoods.getId()); //从映射中移除未变化的数据
				}
			}
			// 保存数据
			for (InStockGoods inStockGoods : inStockGoodsList) {
				if (inStockGoods.getId() == null) {
					insert(inStock.getId(), inStockGoods, dataInStockGoodsList, currentUser); // 新增 
				}
				InStockGoods dataInStockGoods = dataInStockGoodsMap.get(inStockGoods.getId());
				if (dataInStockGoods != null) {
					update(dataInStockGoods, inStockGoods, currentUser); // 修改
					dataInStockGoodsMap.remove(inStockGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
				}
			}
			// 删除数据
			for (Entry<Long, InStockGoods> entry : dataInStockGoodsMap.entrySet()) {
				dataInStockGoodsList.remove(entry.getValue());
				inStockGoodsDao.delete(entry.getKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(InStockGoods dataInStockGoods, InStockGoods inStockGoods, User currentUser) {
		dataInStockGoods.setGoodsCategory(inStockGoods.getGoodsCategory());
		dataInStockGoods.setSpecification(inStockGoods.getSpecification());
		dataInStockGoods.setFrameNo(inStockGoods.getFrameNo());
		dataInStockGoods.setAmount(inStockGoods.getAmount());
		dataInStockGoods.setUnit(inStockGoods.getUnit());
		dataInStockGoods.setPrice(inStockGoods.getPrice());
		dataInStockGoods.setOriginal(inStockGoods.getOriginal());
	}
	
	private void insert(Long pid, InStockGoods inStockGoods, List<InStockGoods> dataInStockGoodsList, User currentUser) {
		inStockGoods.setId(null);
		inStockGoods.setParentId(pid);
		dataInStockGoodsList.add(inStockGoods);
	}
	
}
