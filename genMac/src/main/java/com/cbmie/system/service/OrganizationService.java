package com.cbmie.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.OrganizationDao;
import com.cbmie.system.entity.Organization;

/**
 * 区域service
 */
@Service
@Transactional(readOnly=true)
public class OrganizationService extends BaseService<Organization, Integer>{
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public HibernateDao<Organization, Integer> getEntityDao() {
		return organizationDao;
	}

}
