package com.cbmie.genMac.financial.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.Acceptance;

/**
 * 承兑DAO
 */
@Repository
public class AcceptanceDao extends HibernateDao<Acceptance, Long>{
	
	/**
	 * 找出对应合同下其他承兑总金额
	 * @param contractNo 合同号
	 * @param id 排除id
	 * @return
	 */
	public Double sum(String contractNo, Long id) {
		String sql = "SELECT SUM(rmb) FROM acceptance "
				+ "WHERE invoice_no IN(SELECT invoice_no FROM invoice_reg WHERE contract_no = ?) AND id != ? AND state = '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		sqlQuery.setParameter(1, id);
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}
	
}
