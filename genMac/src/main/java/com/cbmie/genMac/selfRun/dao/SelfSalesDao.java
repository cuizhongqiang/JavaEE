package com.cbmie.genMac.selfRun.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.selfRun.entity.SelfSales;

/**
 * 销售合同DAO
 */
@Repository
public class SelfSalesDao extends HibernateDao<SelfSales, Long> {
	
	public SelfSales findByNo(Long id, String contractNo){
		Criteria criteria = getSession().createCriteria(SelfSales.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("contractNo", contractNo));
		return (SelfSales)criteria.uniqueResult();
	}
	
}
