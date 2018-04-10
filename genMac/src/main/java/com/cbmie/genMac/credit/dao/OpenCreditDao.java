package com.cbmie.genMac.credit.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.credit.entity.OpenCredit;

/**
 * 开证DAO
 */
@Repository
public class OpenCreditDao extends HibernateDao<OpenCredit, Long> {
	
	/**
	 * 客户开证总金额
	 */
	public Double sumCustomerOpenCredit(Long customerId) {
		String sql = "SELECT SUM(the_money) FROM open_credit WHERE customer_id = ? AND state= '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, customerId);
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}

}
