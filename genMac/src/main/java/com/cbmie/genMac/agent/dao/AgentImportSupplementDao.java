package com.cbmie.genMac.agent.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.agent.entity.AgentImportSupplement;

/**
 * 代理进口补充DAO
 */
@Repository
public class AgentImportSupplementDao extends HibernateDao<AgentImportSupplement, Long> {
	
	public AgentImportSupplement findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(AgentImportSupplement.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("agreementNo", no));
		return (AgentImportSupplement)criteria.uniqueResult();
	}

}
