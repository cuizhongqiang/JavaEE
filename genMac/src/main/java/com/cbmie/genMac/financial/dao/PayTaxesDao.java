package com.cbmie.genMac.financial.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.PayTaxes;

/**
 * 交税DAO
 */
@Repository
public class PayTaxesDao extends HibernateDao<PayTaxes, Long>{
	
	public PayTaxes findByNo(Long id, String no){
		Criteria criteria = getSession().createCriteria(PayTaxes.class);
		if (id > 0) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("taxNo", no));
		return (PayTaxes)criteria.uniqueResult();
	}
	
	/**
	 * 查询对应合同下的交税
	 */
	@SuppressWarnings("unchecked")
	public List<PayTaxes> findTaxesByContract(String contractNo) {
		String sql = "SELECT * FROM pay_taxes "
				+ "WHERE invoice_no IN(SELECT invoice_no FROM invoice_reg WHERE contract_no = ?) and state = '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PayTaxes.class);
		sqlQuery.setParameter(0, contractNo);
		return sqlQuery.list();
	}
	
	/**
	 * 找出对应合同下其他交税总金额
	 * @param contractNo 合同号
	 * @param id 排除id
	 * @return
	 */
	public Double sum(String contractNo, Long id) {
		String sql = "SELECT SUM(tax_total) FROM pay_taxes "
				+ "WHERE invoice_no IN(SELECT invoice_no FROM invoice_reg WHERE contract_no = ?) AND id != ? AND state = '生效'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		sqlQuery.setParameter(1, id);
		return sqlQuery.uniqueResult() == null ? 0 : (Double)sqlQuery.uniqueResult();
	}
	
}
