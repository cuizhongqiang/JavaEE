package com.cbmie.genMac.baseinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;

/**
 * 关联信息__基本信息DAO
 */
@Repository
public class AffiBaseInfoDao extends HibernateDao<AffiBaseInfo, Long> {

	@SuppressWarnings("unchecked")
	public List<AffiBaseInfo> getCompany(String num) {
		String sql = "SELECT * FROM affi_base_info WHERE status = '0' AND LOCATE(?, customer_type)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(AffiBaseInfo.class);
		sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	public AffiBaseInfo findByNo(Long id, String customerCode) {
		Criteria criteria = getSession().createCriteria(AffiBaseInfo.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("customerCode", customerCode));
		return (AffiBaseInfo)criteria.uniqueResult();
	}
	
}
