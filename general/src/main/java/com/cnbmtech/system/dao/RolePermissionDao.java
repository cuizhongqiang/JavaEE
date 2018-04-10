package com.cnbmtech.system.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.RolePermission;

@Repository
public interface RolePermissionDao extends BaseDao<RolePermission, Long> {
	
}
