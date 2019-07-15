package com.cbmie.genMac.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.credit.dao.OpenCreditHistoryDao;
import com.cbmie.genMac.credit.entity.OpenCreditHistory;

/**
 * 开证service
 */
@Service
@Transactional
public class OpenCreditHistoryService extends BaseService<OpenCreditHistory, Long> {

	@Autowired
	private OpenCreditHistoryDao openCreditHistoryDao;

	@Override
	public HibernateDao<OpenCreditHistory, Long> getEntityDao() {
		return openCreditHistoryDao;
	}
	
}
