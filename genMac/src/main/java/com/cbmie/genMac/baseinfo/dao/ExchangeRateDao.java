package com.cbmie.genMac.baseinfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.ExchangeRate;

/**
 * 汇率DAO
 */
@Repository
public class ExchangeRateDao extends HibernateDao<ExchangeRate, Long>{

	/**
	 * 找出最新所有币种最新汇率信息
	 */
	@SuppressWarnings("unchecked")
	public List<ExchangeRate> getNewExchangeRate() {
		String sql = "SELECT * FROM exchange_rate a,"
				+ "(SELECT currency,MAX(show_time) maxTime FROM exchange_rate GROUP BY currency) b "
				+ "WHERE a.currency = b.currency AND a.SHOW_TIME = b.maxTime";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ExchangeRate.class);
		return sqlQuery.list();
	}
	
	/**
	 * 找出该币种的最新汇率信息
	 */
	public ExchangeRate getThisNewExchangeRate(String currency) {
		String sql = "SELECT * FROM exchange_rate WHERE CURRENCY = ? ORDER BY SHOW_TIME DESC LIMIT 0,1";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ExchangeRate.class);
		sqlQuery.setParameter(0, currency);
		return (ExchangeRate) (sqlQuery.uniqueResult() == null ? new ExchangeRate() : sqlQuery.uniqueResult());
	}
	
}
