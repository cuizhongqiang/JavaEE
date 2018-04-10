package com.cbmie.genMac.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.credit.dao.OpenCreditDao;
import com.cbmie.genMac.credit.entity.OpenCredit;

/**
 * 开证service
 */
@Service
@Transactional
public class OpenCreditService extends BaseService<OpenCredit, Long> {

	@Autowired
	private OpenCreditDao openCreditDao;

	@Override
	public HibernateDao<OpenCredit, Long> getEntityDao() {
		return openCreditDao;
	}
	
	/**
	 * 客户开证总金额
	 */
	public Double sumCustomerOpenCredit(Long customerId) {
		return openCreditDao.sumCustomerOpenCredit(customerId);
	}
	
}
