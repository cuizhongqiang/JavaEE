package com.cbmie.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.UserRole;


/**
 * 用户角色DAO
 */
@Repository
public class UserRoleDao extends HibernateDao<UserRole, Integer>{

	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleId
	 */
	public void deleteUR(Integer userId,Integer roleId){
		String hql="delete UserRole ur where ur.user.id=?0 and ur.role.id=?1";
		batchExecute(hql, userId,roleId);
	}
	
	/**
	 * 查询用户拥有的角色id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findRoleIds(Integer userId){
		String hql="select ur.role.id from UserRole ur where ur.user.id=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}
	
}
