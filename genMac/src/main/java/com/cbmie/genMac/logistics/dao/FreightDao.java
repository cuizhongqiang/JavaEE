package com.cbmie.genMac.logistics.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.Freight;

/**
 * 货代DAO
 */
@Repository
public class FreightDao extends HibernateDao<Freight, Long> {
	
	/**
	 * 已经确认货代的合同号
	 */
	@SuppressWarnings("unchecked")
	public List<String> getHavFreCons() {
		String sql = "SELECT DISTINCT(contract_no) FROM freight where state = '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}
	
}
