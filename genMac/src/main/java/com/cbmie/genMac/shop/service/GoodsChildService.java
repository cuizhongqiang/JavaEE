package com.cbmie.genMac.shop.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.cbmie.genMac.shop.dao.GoodsChildDao;
import com.cbmie.genMac.shop.entity.Goods;
import com.cbmie.genMac.shop.entity.GoodsChild;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 商品child service
 */
@Service
@Transactional
public class GoodsChildService extends BaseService<GoodsChild, Long> {
	
	@Autowired
	private GoodsChildDao goodsChildDao;

	@Override
	public HibernateDao<GoodsChild, Long> getEntityDao() {
		return goodsChildDao;
	}

	public void save(Goods goods, String goodsChildJson) {
		// 转成标准的json字符串
		goodsChildJson = StringEscapeUtils.unescapeHtml4(goodsChildJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<GoodsChild> goodsChildList = new ArrayList<GoodsChild>();
		try {
			JsonNode jsonNode = objectMapper.readTree(goodsChildJson);
			for (JsonNode jn : jsonNode) {
				GoodsChild goodsChild = objectMapper.readValue(jn.toString(), GoodsChild.class);
				goodsChildList.add(goodsChild);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取goods中goodsChild持久化对象
		List<GoodsChild> dataGoodsChildList = goods.getGoodsChild();
		// 将数据库数据放入映射
		Map<Long, GoodsChild> dataGoodsChildMap = new HashMap<Long, GoodsChild>(); 
		for (GoodsChild dataGoodsChild : dataGoodsChildList) {
			dataGoodsChildMap.put(dataGoodsChild.getId(), dataGoodsChild);
		}
		// 排除没有发生改变的
		for (GoodsChild dataGoodsChild : dataGoodsChildList) {
			if (goodsChildList.contains(dataGoodsChild)) {
				goodsChildList.remove(dataGoodsChild);
				dataGoodsChildMap.remove(dataGoodsChild.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (GoodsChild goodsChild : goodsChildList) {
			if (goodsChild.getId() == null) {
				insert(goods.getId(), goodsChild, dataGoodsChildList); // 新增 
			}
			GoodsChild dataGoodsChild = dataGoodsChildMap.get(goodsChild.getId());
			if (dataGoodsChild != null) {
				update(dataGoodsChild, goodsChild); // 修改
				dataGoodsChildMap.remove(goodsChild.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, GoodsChild> entry : dataGoodsChildMap.entrySet()) {
			dataGoodsChildList.remove(entry.getValue());
			goodsChildDao.delete(entry.getKey());
		}
	}

	private void update(GoodsChild dataGoodsChild, GoodsChild goodsChild) {
		User currentUser = UserUtil.getCurrentUser();
		dataGoodsChild.setName(goodsChild.getName());
		dataGoodsChild.setCount(goodsChild.getCount());
		dataGoodsChild.setType(goodsChild.getType());
		dataGoodsChild.setState(goodsChild.getState());
		dataGoodsChild.setUpdaterNo(currentUser.getLoginName());
		dataGoodsChild.setUpdaterName(currentUser.getName());
		dataGoodsChild.setUpdateDate(new Date());
	}
	
	private void insert(Long pid, GoodsChild goodsChild, List<GoodsChild> dataGoodsChildList) {
		User currentUser = UserUtil.getCurrentUser();
		goodsChild.setPid(pid);
		goodsChild.setCreaterNo(currentUser.getLoginName());
		goodsChild.setCreaterName(currentUser.getName());
		goodsChild.setCreateDate(new Date());
		dataGoodsChildList.add(goodsChild);
	}

}
