package com.cbmie.genMac.logistics.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.SendGoods;

/**
 * 放货DAO
 */
@Repository
public class SendGoodsDao extends HibernateDao<SendGoods, Long> {
	
	public SendGoods findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(SendGoods.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("sendGoodsNo", no));
		return (SendGoods)criteria.uniqueResult();
	}
	
	/**
	 * 找出"走库存"且"生效"的出库,并按出库时间进行排序
	 */
	@SuppressWarnings("unchecked")
	public List<SendGoods> findForPlan() {
		String sql = "SELECT * FROM send_goods where state = '生效' and !ISNULL(in_stock_id) and in_stock_id != '' ORDER BY send_date";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SendGoods.class);
		return sqlQuery.list();
	}
	
}
