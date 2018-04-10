package com.cbmie.genMac.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.FreightDao;
import com.cbmie.genMac.logistics.entity.Freight;

/**
 * 货代service
 */
@Service
@Transactional
public class FreightService extends BaseService<Freight, Long> {

	@Autowired
	private FreightDao freightDao;

	@Override
	public HibernateDao<Freight, Long> getEntityDao() {
		return freightDao;
	}
	
	public List<String> getHavFreCons() {
		return freightDao.getHavFreCons();
	}
	
}
