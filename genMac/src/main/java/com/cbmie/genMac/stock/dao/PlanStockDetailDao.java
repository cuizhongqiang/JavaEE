package com.cbmie.genMac.stock.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.stock.entity.PlanStockDetail;

/**
 * 盘库明细DAO
 */
@Repository
public class PlanStockDetailDao extends HibernateDao<PlanStockDetail, Long>{
	
	/**
	 * 根据仓库名称获取商品目录
	 */
	@SuppressWarnings("unchecked")
	public List<String> findGoodsForWarehouse(String warehouseName) {
		String sql = "SELECT DISTINCT(CONCAT(goods_category,'/',specification)) FROM in_stock_goods isg LEFT JOIN in_stock iss ON "
				+ "isg.parent_id = iss.id WHERE iss.storage_unit = ?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, warehouseName);
		return sqlQuery.list();
	}
	
	/**
	 * 根据仓库名称和商品获取账面数
	 */
	public Double findGoodsSumForWarehouse(String warehouseName, String goodsNameSpecification, Long id) {
		String sqlIn = "SELECT SUM(amount) FROM in_stock_goods isg LEFT JOIN in_stock iss ON isg.parent_id = iss.id "
				+ "WHERE iss.storage_unit = ? AND CONCAT(isg.goods_category,'/',isg.specification) = ?";
		String sqlOut = "SELECT SUM(amount) FROM send_goods_goods sgg LEFT JOIN send_goods sg ON sgg.parent_id = sg.id "
				+ "WHERE sg.warehouse = ? AND sg.state = '生效' AND CONCAT(sgg.goods_category,'/',sgg.specification) = ?";
		SQLQuery sqlQueryIn = getSession().createSQLQuery(sqlIn);
		sqlQueryIn.setParameter(0, warehouseName);
		sqlQueryIn.setParameter(1, goodsNameSpecification);
		SQLQuery sqlQueryOut = getSession().createSQLQuery(sqlOut);
		sqlQueryOut.setParameter(0, warehouseName);
		sqlQueryOut.setParameter(1, goodsNameSpecification);
		Double in = 0d;
		if (sqlQueryIn.uniqueResult() != null) {
			in = Double.valueOf(String.valueOf(sqlQueryIn.uniqueResult()));
		}
		Double out = 0d;
		if (sqlQueryOut.uniqueResult() != null) {
			out = Double.valueOf(String.valueOf(sqlQueryOut.uniqueResult()));
		}
		Double profit = 0d;
		Double loss = 0d;
		Object obj = findPlanSum(warehouseName, goodsNameSpecification, id);
		if (obj != null) {
			Object[] objArray = (Object[])obj;
			if (objArray[0] != null) {
				profit = Double.valueOf(String.valueOf(objArray[0]));
			}
			if (objArray[1] != null) {
				loss = Double.valueOf(String.valueOf(objArray[1]));
			}
		}
		return in - out + profit - loss;
	}
	
	/**
	 * 获取盘库的亏、盈和
	 * @param warehouseName 仓库名称
	 * @param goodsNameSpecification 商品
	 * @return
	 */
	public Object findPlanSum(String warehouseName, String goodsNameSpecification, Long id) {
		String sqlPlan = "SELECT SUM(profit_num),SUM(loss_num) FROM plan_stock_detail WHERE "
				+ "warehouse_name = ? AND goods_name_specification = ?";
		if (id > 0) {
			sqlPlan += " AND id != ?";
		}
		SQLQuery sqlQueryPlan = getSession().createSQLQuery(sqlPlan);
		sqlQueryPlan.setParameter(0, warehouseName);
		sqlQueryPlan.setParameter(1, goodsNameSpecification);
		if (id > 0) {
			sqlQueryPlan.setParameter(2, id);
		}
		return sqlQueryPlan.uniqueResult();
	}
	
	/**
	 * 获取盘库的亏、盈和
	 * @param warehouseName 仓库名称(name1,name2,name3...)
	 * @param goodsNameSpecification 商品
	 * @return
	 */
	public Object findPlanSum(String warehouseName, String goodsNameSpecification) {
		String sqlPlan = "SELECT SUM(profit_num),SUM(loss_num) FROM plan_stock_detail WHERE "
				+ "warehouse_name in(:names) AND goods_name_specification = :goods";
		SQLQuery sqlQueryPlan = getSession().createSQLQuery(sqlPlan);
		Set<String> set = new HashSet<String>(Arrays.asList(warehouseName.split(",")));
		sqlQueryPlan.setParameterList("names", set);
		sqlQueryPlan.setParameter("goods", goodsNameSpecification);
		return sqlQueryPlan.uniqueResult();
	}
	
}
