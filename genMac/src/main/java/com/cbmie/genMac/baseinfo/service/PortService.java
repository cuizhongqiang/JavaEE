package com.cbmie.genMac.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.PortDao;
import com.cbmie.genMac.baseinfo.entity.Port;

/**
 * 港口service
 */
@Service
@Transactional(readOnly = true)
public class PortService extends BaseService<Port, Long> {

	@Autowired
	private PortDao portDao;

	@Override
	public HibernateDao<Port, Long> getEntityDao() {
		return portDao;
	}

}
