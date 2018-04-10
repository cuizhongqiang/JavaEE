package com.cnbmtech.system.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.UserRole;

@Repository
public interface UserRoleDao extends BaseDao<UserRole, Long> {
	
}
