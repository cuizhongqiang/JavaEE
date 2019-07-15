package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.ExpenseDao;
import com.cbmie.genMac.financial.entity.Expense;

/**
 * 费用service
 */
@Service
@Transactional
public class ExpenseService extends BaseService<Expense, Long> {
	
	@Autowired
	private ExpenseDao expenseDao;

	@Override
	public HibernateDao<Expense, Long> getEntityDao() {
		return expenseDao;
	}
	
	/**
	 * 对外支付总金额
	 */
	public Double sumCustomerPayExpense(Long customerId) {
		return expenseDao.sumCustomerPayExpense(customerId);
	}
	
}
