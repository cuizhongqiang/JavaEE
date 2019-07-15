package com.cbmie.genMac.stock.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.stock.entity.PlanStock;

/**
 * 盘库DAO
 */
@Repository
public class PlanStockDao extends HibernateDao<PlanStock, Long>{
	
}
