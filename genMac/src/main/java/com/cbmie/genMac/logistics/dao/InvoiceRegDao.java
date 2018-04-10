package com.cbmie.genMac.logistics.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.InvoiceReg;

/**
 * 到单登记DAO
 */
@Repository
public class InvoiceRegDao extends HibernateDao<InvoiceReg, Long> {
	
	public InvoiceReg findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(InvoiceReg.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("invoiceNo", no));
		return (InvoiceReg)criteria.uniqueResult();
	}
	
	/**
	 * 查询已经确认货代的到单
	 * @param inventoryWay 库存方式(库存业务--入库，直运业务--放货)
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceReg> findHaveFreight(String inventoryWay) {
		StringBuffer sqlSB = new StringBuffer("SELECT * FROM invoice_reg WHERE 1=1");
		if (!inventoryWay.equalsIgnoreCase("all")) {
			sqlSB.append(" AND inventory_way = '" + inventoryWay + "'");
		}
		sqlSB.append(" AND invoice_no IN(SELECT invoice_no FROM freight WHERE state = '生效')");
		SQLQuery sqlQuery = getSession().createSQLQuery(sqlSB.toString()).addEntity(InvoiceReg.class);
		return sqlQuery.list();
	}
	
	/**
	 * 到单付汇预警
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceReg> findInvoiceAcceptance() {
		String sql = "SELECT * FROM invoice_reg "
				+ "WHERE datediff(accept_date, sysdate()) < 5 AND invoice_no NOT IN(SELECT invoice_no FROM acceptance WHERE state = '生效')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(InvoiceReg.class);
		return sqlQuery.list();
	}
	
}
