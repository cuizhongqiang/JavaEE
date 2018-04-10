package com.cbmie.genMac.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.GoodsManageDao;
import com.cbmie.genMac.baseinfo.entity.GoodsManage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 商品管理service
 */
@Service
@Transactional
public class GoodsManageService extends BaseService<GoodsManage, Long> {

	@Autowired
	private GoodsManageDao goodsManageDao;

	@Override
	public HibernateDao<GoodsManage, Long> getEntityDao() {
		return goodsManageDao;
	}
	
	public String getMaxCode(Long pid) {
		String maxcodeStr = goodsManageDao.getMaxCode(pid);
		if (maxcodeStr.equals("null")) {
			GoodsManage gm = goodsManageDao.get(pid);
			return gm == null ? "01" : gm.getGoodsCode() + "01";
		}
		Long maxNext = Long.valueOf(maxcodeStr) + 1;
		String maxNextStr = String.valueOf(maxNext);
		StringBuffer sb = new StringBuffer();
		// 补位
		for (int i = 0; i < (maxcodeStr.length() - maxNextStr.length()); i++) {
			sb.append("0");
		}
		return sb.toString() + maxNextStr;
	}
	
	public GoodsManage findByNo(Long id, String no){
		return goodsManageDao.findByNo(id, no);
	}
	
	public List<GoodsManage> search(List<PropertyFilter> filters) {
		return goodsManageDao.search(filters);
	}
	
	public List<GoodsManage> getIsLeaf() {
		return goodsManageDao.getIsLeaf();
	}
	
	public String findBy(String code) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(goodsManageDao.findBy("goodsCode", code).get(0));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
