package com.cbmie.genMac.financial.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.Expense;

/**
 * 费用DAO
 */
@Repository
public class ExpenseDao extends HibernateDao<Expense, Long>{
	
	/**
	 * 对外支付总金额
	 */
	public Double sumCustomerPayExpense(Long customerId) {
		String sql = "SELECT sum(sum_money) FROM expense e "
				+ "WHERE e.customer_id = ? AND e.state= '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, customerId);
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}
	
}
