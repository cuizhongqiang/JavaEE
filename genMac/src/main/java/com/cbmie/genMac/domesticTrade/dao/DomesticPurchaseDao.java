package com.cbmie.genMac.domesticTrade.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchase;

/**
 * 采购合同DAO
 */
@Repository
public class DomesticPurchaseDao extends HibernateDao<DomesticPurchase, Long> {

	public DomesticPurchase findByNo(Long id, String contractNo){
		Criteria criteria = getSession().createCriteria(DomesticPurchase.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("contractNo", contractNo));
		return (DomesticPurchase)criteria.uniqueResult();
	}
	
}
