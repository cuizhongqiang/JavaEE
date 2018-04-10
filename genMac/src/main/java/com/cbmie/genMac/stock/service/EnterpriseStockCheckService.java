package com.cbmie.genMac.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.stock.dao.EnterpriseStockCheckDao;
import com.cbmie.genMac.stock.entity.EnterpriseStockCheck;

/**
 * 仓储企业评审service
 */
@Service
@Transactional
public class EnterpriseStockCheckService extends BaseService<EnterpriseStockCheck, Long> {
	
	@Autowired
	private EnterpriseStockCheckDao enterpriseStockCheckDao;

	@Override
	public HibernateDao<EnterpriseStockCheck, Long> getEntityDao() {
		return enterpriseStockCheckDao;
	}
	

}
