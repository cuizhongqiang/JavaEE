package com.cbmie.system.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Organization;


/**
 * 机构DAO
 */
@Repository
public class OrganizationDao extends HibernateDao<Organization, Integer>{

}
