package com.cbmie.genMac.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.financial.entity.Serial;

/**
 * 水单DAO
 */
@Repository
public class SerialDao extends HibernateDao<Serial, Long>{
	
	public Serial findByNo(Long id, String no) {
		Criteria criteria = getSession().createCriteria(Serial.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("serialNumber", no));
		return (Serial)criteria.uniqueResult();
	}
	
	/**
	 * 找出对应合同下对应类型水单的总金额
	 */
	public Double sum(String contractNo, String serialCategory) {
		String sql = "SELECT SUM(money) FROM serial WHERE contract_no = ? AND serial_category = ?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		sqlQuery.setParameter(1, serialCategory);
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}
	
	/**
	 * 客户来款水单总金额
	 */
	public Double sumCustomerSerial(Long customerId, String serialCategory) {
		String sql = "SELECT SUM(money) FROM serial WHERE customer_id = ?";
		if (StringUtils.isNotEmpty(serialCategory)) {
			sql += " And serial_category = ?";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, customerId);
		if (StringUtils.isNotEmpty(serialCategory)) {
			sqlQuery.setParameter(1, serialCategory);
		}
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}
	
}
