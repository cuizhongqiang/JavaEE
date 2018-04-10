package com.cnbmtech.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.system.dao.UserRoleDao;
import com.cnbmtech.system.entity.UserRole;

@Service
public class UserRoleService extends BaseService<UserRole, Long> {

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public BaseDao<UserRole, Long> getEntityDao() {
		return userRoleDao;
	}

	@Transactional
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM sys_user_role WHERE id = ?", id);
	}

}
