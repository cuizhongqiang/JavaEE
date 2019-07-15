package com.cbmie.genMac.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.credit.dao.OpenCreditHistoryGoodsDao;
import com.cbmie.genMac.credit.entity.OpenCreditHistoryGoods;

/**
 * 开证历史商品service
 */
@Service
@Transactional
public class OpenCreditHistoryGoodsService extends BaseService<OpenCreditHistoryGoods, Long> {

	@Autowired
	private OpenCreditHistoryGoodsDao openCreditHistoryGoodsDao;

	@Override
	public HibernateDao<OpenCreditHistoryGoods, Long> getEntityDao() {
		return openCreditHistoryGoodsDao;
	}
	
}
