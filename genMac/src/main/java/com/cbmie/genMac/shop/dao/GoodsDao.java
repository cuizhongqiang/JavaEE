package com.cbmie.genMac.shop.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.shop.entity.Goods;

/**
 * 商品DAO
 */
@Repository
public class GoodsDao extends HibernateDao<Goods, Long>{

}
