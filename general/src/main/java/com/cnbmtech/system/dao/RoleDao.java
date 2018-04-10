package com.cnbmtech.system.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.Role;

@Repository
public interface RoleDao extends BaseDao<Role, Long> {
	
}
