package com.cbmie.system.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.DictMain;

/**
 * 字典主表DAO
 */
@Repository
public class DictMainDao extends HibernateDao<DictMain, Integer>{

	public DictMain findByCode(String code) {
		Criteria criteria = getSession().createCriteria(DictMain.class);
		criteria.add(Restrictions.eq("code", code));
		return (DictMain)criteria.uniqueResult();
	}
	
}
