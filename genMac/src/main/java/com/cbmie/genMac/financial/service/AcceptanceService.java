package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.AcceptanceDao;
import com.cbmie.genMac.financial.entity.Acceptance;

/**
 * 承兑service
 */
@Service
@Transactional
public class AcceptanceService extends BaseService<Acceptance, Long> {
	
	@Autowired
	private AcceptanceDao acceptanceDao;

	@Override
	public HibernateDao<Acceptance, Long> getEntityDao() {
		return acceptanceDao;
	}
	
	public Double sum(String contractNo, Long id) {
		return acceptanceDao.sum(contractNo, id);
	}
	
}
