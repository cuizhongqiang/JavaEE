package com.cbmie.genMac.baseinfo.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.Port;

/**
 * 港口DAO
 */
@Repository
public class PortDao extends HibernateDao<Port, Long>{

}
