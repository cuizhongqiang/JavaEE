package com.cbmie.genMac.domesticTrade.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;

/**
 * 代理采购合同DAO
 */
@Repository
public class AgentPurchaseDao extends HibernateDao<AgentPurchase, Long> {
	
	public AgentPurchase findByNo(Long id, String contractNo){
		Criteria criteria = getSession().createCriteria(AgentPurchase.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("contractNo", contractNo));
		return (AgentPurchase)criteria.uniqueResult();
	}
}
