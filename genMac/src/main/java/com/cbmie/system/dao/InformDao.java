package com.cbmie.system.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Inform;


/**
 * 系统通知DAO
 */
@Repository
public class InformDao extends HibernateDao<Inform, Integer>{
	
	public Inform findBusinessInform(String className, Long businessId){
		Criteria criteria = getSession().createCriteria(Inform.class);
		criteria.add(Restrictions.eq("className", className));
		criteria.add(Restrictions.eq("businessId", businessId));
		return (Inform)criteria.uniqueResult();
	}

}
