package com.cbmie.genMac.financial.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.ExpenseDetail;

/**
 * 付费DAO
 */
@Repository
public class ExpenseDetailDao extends HibernateDao<ExpenseDetail, Long> {

}
