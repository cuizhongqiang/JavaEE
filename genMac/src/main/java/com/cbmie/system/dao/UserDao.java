package com.cbmie.system.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.User;


/**
 * 用户DAO
 */
@Repository
public class UserDao extends HibernateDao<User, Integer>{
	 
	
	@SuppressWarnings("unchecked")
	public List<User> getSupplier(String num) {
		String sql = "select * from user where id in (select user_id from user_role where role_id=?);";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(User.class);
		sqlQuery.setParameter(0, num);
		List<User> list = sqlQuery.list();
		return list;
	}

}
