package com.cnbmtech.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.Permission;

@Repository
public interface PermissionDao extends BaseDao<Permission, Long> {
	
	public List<Permission> findByStatusOrderBySortAsc(int status);
	
}
