package com.cbmie.genMac.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.stock.dao.PlanStockDao;
import com.cbmie.genMac.stock.entity.PlanStock;

/**
 * 盘库service
 */
@Service
@Transactional
public class PlanStockService extends BaseService<PlanStock, Long> {
	
	@Autowired
	private PlanStockDao planStockDao;

	@Override
	public HibernateDao<PlanStock, Long> getEntityDao() {
		return planStockDao;
	}
	
}
