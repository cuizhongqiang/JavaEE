package com.cbmie.genMac.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.PayTaxesDao;
import com.cbmie.genMac.financial.entity.PayTaxes;

/**
 * 交税service
 */
@Service
@Transactional
public class PayTaxesService extends BaseService<PayTaxes, Long> {
	
	@Autowired
	private PayTaxesDao payTaxesDao;

	@Override
	public HibernateDao<PayTaxes, Long> getEntityDao() {
		return payTaxesDao;
	}
	
	public PayTaxes findByNo(Long id, String no) {
		return payTaxesDao.findByNo(id, no);
	}
	
	public List<PayTaxes> findTaxesByContract(String contractNo) {
		return payTaxesDao.findTaxesByContract(contractNo);
	}
	
	public Double sum(String contractNo, Long id) {
		return payTaxesDao.sum(contractNo, id);
	}
	
}
