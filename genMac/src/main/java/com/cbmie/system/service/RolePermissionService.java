package com.cbmie.system.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.RolePermissionDao;
import com.cbmie.system.entity.Permission;
import com.cbmie.system.entity.Role;
import com.cbmie.system.entity.RolePermission;

/**
 * 角色权限service
 */
@Service
@Transactional(readOnly=true)
public class RolePermissionService extends BaseService<RolePermission, Integer> {
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Override
	public HibernateDao<RolePermission, Integer> getEntityDao() {
		return rolePermissionDao;
	}
	
	/**
	 * 获取角色权限id集合
	 * @param id
	 * @return List
	 */
	public List<Integer> getPermissionIds(Integer roleId){
		return rolePermissionDao.findPermissionIds(roleId);
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateRolePermission(Integer id,List<Integer> oldList,List<Integer> newList){
		//是否删除
		for(int i=0,j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i))){
				rolePermissionDao.deleteRP(id,oldList.get(i));
			}
		}
		
		//是否添加
		for(int i=0,j=newList.size();i<j;i++){
			if(!oldList.contains(newList.get(i))){
				rolePermissionDao.save(getRolePermission(id,newList.get(i)));
			}
		}
	}
	
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc){
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}
	
	/**
	 * 构造角色权限对象
	 * @param roleId
	 * @param permissionId
	 * @return RolePermission
	 */
	private RolePermission getRolePermission(Integer roleId,Integer permissionId){
		RolePermission rp=new RolePermission();
		rp.setRole(new Role(roleId));
		rp.setPermission(new Permission(permissionId));
		return rp;
	}
	
}
