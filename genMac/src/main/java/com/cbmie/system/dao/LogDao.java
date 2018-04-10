package com.cbmie.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Log;


/**
 * 日志DAO
 */
@Repository
public class LogDao extends HibernateDao<Log, Integer>{
	
	/**
	 * 批量删除日志
	 * @param ids 日志id列表
	 */
	public void deleteBatch(List<Integer> idList){
		String hql="delete from Log log where log.id in (:idList)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("idList", idList);
		query.executeUpdate();
	}
}
