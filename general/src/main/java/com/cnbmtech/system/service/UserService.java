package com.cnbmtech.system.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.base.utils.security.Digests;
import com.cnbmtech.base.utils.security.Encodes;
import com.cnbmtech.system.dao.UserDao;
import com.cnbmtech.system.entity.Permission;
import com.cnbmtech.system.entity.Role;
import com.cnbmtech.system.entity.User;
import com.cnbmtech.system.entity.UserRole;

@Service
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public BaseDao<User, Long> getEntityDao() {
		return userDao;
	}
	
	@CacheEvict(value = "userPermission", key = "#id")
	public void delete(Long id) {
		super.delete(id);
	}
	
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(8);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, 1024);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 验证密码是否正确
	 */
	public boolean checkPassword(User user, String oldPassword) {
		byte[] salt = Encodes.decodeHex(user.getSalt());
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(), salt, 1024);
		if (user.getPassword().equals(Encodes.encodeHex(hashPassword))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 保存用户角色
	 */
	public void distributionRoleSave(HttpServletRequest request) {
		String[] roleIds = request.getParameter("roleIds").split(",");
		Set<Long> roleIdSet = new HashSet<Long>();
		for (String roleId : roleIds) {
			if (roleId.trim().length() > 0) {
				roleIdSet.add(Long.valueOf(roleId));
			}
		}
		Long userId = Long.valueOf(request.getParameter("userId"));
		User user = get(userId);
		List<UserRole> userRoles = user.getUserRoles();
		//处理重复,删除多余
		for (UserRole userRole : userRoles) {
			Long roleId = userRole.getRole().getId();
			if (!roleIdSet.remove(roleId)) {
				userRoleService.deleteById(userRole.getId());
			}
		}
		//新增
		for (Long roleId : roleIdSet) {
			Role role = roleService.get(roleId);
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			userRoleService.save(userRole);
		}
	}
	
	/**
	 * 获取用户下的角色
	 */
	public List<Role> getUserRoles(Long userId) {
		String sql = "SELECT sr.* FROM "
				+ "sys_user su LEFT JOIN sys_user_role sur ON su.id = sur.user_id "
				+ "LEFT JOIN sys_role sr ON sr.id = sur.role_id "
				+ "WHERE su.id = ?";
		return jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<Role>(){

			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("name"));
				role.setCode(rs.getString("code"));
				role.setDescription(rs.getString("description"));
				return role;
			}
			
		});
	}
	
	/**
	 * 获取用户下的权限
	 */
	public List<Permission> getUserPermission(Long userId) {
		String sql = "SELECT DISTINCT(sp.id),sp.* FROM sys_user su "
				+ "LEFT JOIN sys_user_role sur ON su.id = sur.user_id "
				+ "LEFT JOIN sys_role sr ON sr.id = sur.role_id "
				+ "LEFT JOIN sys_role_permission srp on sr.id = srp.role_id "
				+ "LEFT JOIN sys_permission sp on sp.id = srp.permission_id "
				+ "WHERE su.id = ? AND sp.status = 1 ORDER BY sp.sort";
		return jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<Permission>(){

			@Override
			public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
				Permission permission = new Permission();
				permission.setId(rs.getLong("id"));
				permission.setParentId(rs.getLong("parent_id"));
				permission.setName(rs.getString("name"));
				permission.setUrl(rs.getString("url"));
				permission.setIcon(rs.getString("icon"));
				permission.setType(rs.getString("type"));
				return permission;
			}
			
		});
	}
	
	/**
	 * Cacheable：当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回了。
	 */
	@Cacheable(value = "userPermission", key = "#userId")
	public List<Permission> userPermissionCacheable(Long userId) {
		return getUserPermission(userId);
	}
	
	/**
	 * CachePut：这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中。
	 */
	@CachePut(value = "userPermission", key = "#userId")
	public List<Permission> userPermissionCachePut(Long userId) {
		return getUserPermission(userId);
	}
	
	/**
	 * 根据角色id获取用户id集合
	 */
	public List<Long> getUserIdsByRoleId(Long roleId) {
		String sql = "SELECT DISTINCT(su.id) FROM "
				+ "sys_user su LEFT JOIN sys_user_role sur ON su.id = sur.user_id "
				+ "LEFT JOIN sys_role sr ON sr.id = sur.role_id "
				+ "WHERE sr.id = ?";
		return jdbcTemplate.queryForList(sql, Long.class, roleId);
	}
	
	/**
	 * 根据权限id获取用户id集合
	 */
	public List<Long> getUserIdsByPermissionId(Long permissionId) {
		String sql = "SELECT DISTINCT(su.id) FROM sys_user su "
				+ "LEFT JOIN sys_user_role sur ON su.id = sur.user_id "
				+ "LEFT JOIN sys_role sr ON sr.id = sur.role_id "
				+ "LEFT JOIN sys_role_permission srp on sr.id = srp.role_id "
				+ "LEFT JOIN sys_permission sp on sp.id = srp.permission_id "
				+ "WHERE sp.id = ?";
		return jdbcTemplate.queryForList(sql, Long.class, permissionId);
	}
	
	public Integer uniqueUserName(String userName, Long id) {
		String sql = "SELECT count(0) FROM sys_user WHERE user_name = ? AND id != ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{userName, id});
	}
	
}
