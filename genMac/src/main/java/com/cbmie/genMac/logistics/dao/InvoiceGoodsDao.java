package com.cbmie.genMac.logistics.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.InvoiceGoods;

/**
 * 到单商品DAO
 */
@Repository
public class InvoiceGoodsDao extends HibernateDao<InvoiceGoods, Long> {
	
}
