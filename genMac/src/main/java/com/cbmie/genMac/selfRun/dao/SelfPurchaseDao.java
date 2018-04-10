package com.cbmie.genMac.selfRun.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.selfRun.entity.SelfPurchase;

/**
 * 采购合同DAO
 */
@Repository
public class SelfPurchaseDao extends HibernateDao<SelfPurchase, Long> {
	
	public SelfPurchase findByNo(Long id, String contractNo){
		Criteria criteria = getSession().createCriteria(SelfPurchase.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("contractNo", contractNo));
		return (SelfPurchase)criteria.uniqueResult();
	}
	
}
