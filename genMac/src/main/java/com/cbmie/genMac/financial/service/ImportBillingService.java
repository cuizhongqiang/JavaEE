package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.ImportBillingDao;
import com.cbmie.genMac.financial.entity.ImportBilling;

/**
 * 进口结算联系单service
 */
@Service
@Transactional
public class ImportBillingService extends BaseService<ImportBilling, Long> {
	
	@Autowired
	private ImportBillingDao importBillingDao;

	@Override
	public HibernateDao<ImportBilling, Long> getEntityDao() {
		return importBillingDao;
	}
	
}
