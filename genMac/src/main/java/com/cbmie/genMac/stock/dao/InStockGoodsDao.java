package com.cbmie.genMac.stock.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.stock.entity.InStockGoods;

/**
 * DAO
 */
@Repository
public class InStockGoodsDao extends HibernateDao<InStockGoods, Long>{

}
