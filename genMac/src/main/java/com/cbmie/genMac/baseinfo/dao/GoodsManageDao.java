package com.cbmie.genMac.baseinfo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.genMac.baseinfo.entity.GoodsManage;

/**
 * 商品管理DAO
 */
@Repository
public class GoodsManageDao extends HibernateDao<GoodsManage, Long> {
	
	/**
	 * 获取子商品中最大的商品编码
	 */
	public String getMaxCode(Long pid) {
		String sql = "SELECT MAX(goodscode) FROM goods_manage WHERE pid = ?";
		if (pid == 0) {
			sql = "SELECT MAX(goodscode) FROM goods_manage WHERE ISNULL(pid)";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (pid > 0) {
			sqlQuery.setParameter(0, pid);
		}
		return String.valueOf(sqlQuery.uniqueResult());
	}
	
	/**
	 * 商品编码唯一性验证
	 */
	public GoodsManage findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(GoodsManage.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("goodsCode", no));
		return (GoodsManage)criteria.uniqueResult();
	}

	/**
	 * 条件查询
	 */
	public List<GoodsManage> search(List<PropertyFilter> filters) {
		List<GoodsManage> list = new ArrayList<GoodsManage>();
		// 父商品
		List<GoodsManage> listPar = find(filters);
		list.addAll(listPar);
		// 子商品
		getSon(listPar, list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void getSon (List<GoodsManage> listPar, List<GoodsManage> list) {
		for (GoodsManage gm : listPar) {
			Criteria criteria = getSession().createCriteria(GoodsManage.class);
			criteria.add(Restrictions.eq("pid", gm.getId()));
			List<GoodsManage> listSon = criteria.list();
			for (GoodsManage gms : listSon) {
				if (!list.contains(gms)) {
					list.add(gms);
				}
			}
			getSon(listSon, list);
		}
	}
	
	/**
	 * 获取所有无子节点的叶子节点
	 */
	@SuppressWarnings("unchecked")
	public List<GoodsManage> getIsLeaf() {
		String sql = "SELECT * FROM goods_manage WHERE id NOT IN(SELECT pid FROM goods_manage WHERE pid IS NOT NULL)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(GoodsManage.class);
		return sqlQuery.list();
	}
}
