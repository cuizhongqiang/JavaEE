package com.cbmie.genMac.stock.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.stock.entity.InStock;

/**
 * 入库DAO
 */
@Repository
public class InStockDao extends HibernateDao<InStock, Long> {

	public InStock findByNo(Long id, String no) {
		Criteria criteria = getSession().createCriteria(InStock.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("inStockId", no));
		return (InStock) criteria.uniqueResult();
	}

}
