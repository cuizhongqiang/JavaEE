package com.cbmie.genMac.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.shop.dao.GoodsDao;
import com.cbmie.genMac.shop.entity.Goods;

/**
 * 商品service
 */
@Service
@Transactional(readOnly=true)
public class GoodsService extends BaseService<Goods, Long> {
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public HibernateDao<Goods, Long> getEntityDao() {
		return goodsDao;
	}

}
