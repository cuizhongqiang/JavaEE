package com.cbmie.genMac.baseinfo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.Warehouse;

/**
 * 仓库DAO
 */
@Repository
public class WarehouseDao extends HibernateDao<Warehouse, Long>{

	public Warehouse findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(Warehouse.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("warehouseCode", no));
		return (Warehouse)criteria.uniqueResult();
	}
	
}
