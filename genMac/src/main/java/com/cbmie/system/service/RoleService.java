package com.cbmie.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.RoleDao;
import com.cbmie.system.entity.Role;

/**
 * 角色service
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, Integer> {

	@Autowired
	private RoleDao roleDao;

	@Override
	public HibernateDao<Role, Integer> getEntityDao() {
		return roleDao;
	}
	
	/**
	 * 按id查询角色
	 * @param id
	 * @return 角色对象
	 */
	public Role getRole(Integer id) {
		return roleDao.findUniqueBy("id", id);
	}
	
	/**
	 * 按roleCode查询角色
	 * @param roleCode
	 * @return 角色对象
	 */
	public Role getRole(String roleCode) {
		return roleDao.findUniqueBy("roleCode", roleCode);
	}
}
