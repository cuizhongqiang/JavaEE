package com.cbmie.genMac.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.ExchangeRateDao;
import com.cbmie.genMac.baseinfo.entity.ExchangeRate;

/**
 * 汇率service
 */
@Service
@Transactional(readOnly = true)
public class ExchangeRateService extends BaseService<ExchangeRate, Long> {

	@Autowired
	private ExchangeRateDao exchangeRateDao;

	@Override
	public HibernateDao<ExchangeRate, Long> getEntityDao() {
		return exchangeRateDao;
	}
	
	public List<ExchangeRate> getNewExchangeRate() {
		return exchangeRateDao.getNewExchangeRate();
	}
	
	public ExchangeRate getThisNewExchangeRate(String currency) {
		return exchangeRateDao.getThisNewExchangeRate(currency);
	}

}
