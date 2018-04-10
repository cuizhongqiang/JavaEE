package com.cnbmtech.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.system.dao.RolePermissionDao;
import com.cnbmtech.system.entity.RolePermission;

@Service
public class RolePermissionService extends BaseService<RolePermission, Long> {

	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public BaseDao<RolePermission, Long> getEntityDao() {
		return rolePermissionDao;
	}

	@Transactional
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM sys_role_permission WHERE id = ?", id);
	}

}
