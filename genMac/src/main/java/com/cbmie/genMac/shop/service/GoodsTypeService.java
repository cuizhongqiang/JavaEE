package com.cbmie.genMac.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.shop.dao.GoodsTypeDao;
import com.cbmie.genMac.shop.entity.GoodsType;

/**
 * 商品类型service
 */
@Service
@Transactional(readOnly=true)
public class GoodsTypeService extends BaseService<GoodsType, Long> {
	
	@Autowired
	private GoodsTypeDao goodsTypeDao;

	@Override
	public HibernateDao<GoodsType, Long> getEntityDao() {
		return goodsTypeDao;
	}

}
