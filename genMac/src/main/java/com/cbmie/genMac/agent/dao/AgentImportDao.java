package com.cbmie.genMac.agent.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.agent.entity.AgentImport;

/**
 * 代理进口DAO
 */
@Repository
public class AgentImportDao extends HibernateDao<AgentImport, Long> {
	
	public AgentImport findByNo(Long id, String contractNo, String foreignContractNo){
		Criteria criteria = getSession().createCriteria(AgentImport.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		Disjunction dis = Restrictions.disjunction();
		dis.add(Restrictions.eq("contractNo", contractNo));
		dis.add(Restrictions.eq("foreignContractNo", foreignContractNo));
		criteria.add(dis);
		return (AgentImport)criteria.uniqueResult();
	}
	
	/**
	 * 找出满足催缴保证金的进口合同
	 */
	@SuppressWarnings("unchecked")
	public List<AgentImport> findMarginInform() {
		String sql = "SELECT * FROM agent_import where state = '生效' "
				+ "and datediff(open_credit_date, sysdate()) < 5 "
				+ "and contract_no not in(SELECT contract_no FROM serial WHERE serial_category = '保证金')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(AgentImport.class);
		return sqlQuery.list();
	}
	
}
