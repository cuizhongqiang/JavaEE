package com.cnbmtech.base.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.entity.Accessory;

/**
 * 附件DAO
 */
@Repository
public interface AccessoryDao extends BaseDao<Accessory, Long>{

	public Accessory findByAccId(Long accId);

}
