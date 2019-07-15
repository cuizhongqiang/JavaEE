package com.cbmie.genMac.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.LogisticsCheckDao;
import com.cbmie.genMac.logistics.entity.LogisticsCheck;

/**
 * 物流合作公司评审service
 */
@Service
@Transactional
public class LogisticsCheckService extends BaseService<LogisticsCheck, Long> {
	
	@Autowired
	private LogisticsCheckDao logisticsCheckDao;

	@Override
	public HibernateDao<LogisticsCheck, Long> getEntityDao() {
		return logisticsCheckDao;
	}
	

}
