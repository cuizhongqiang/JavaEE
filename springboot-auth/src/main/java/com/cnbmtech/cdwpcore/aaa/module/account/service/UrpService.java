package com.cnbmtech.cdwpcore.aaa.module.account.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnbmtech.cdwpcore.aaa.module.account.dao.PermissionRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.RoleInfoRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.RolePermissionRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.UserRoleRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.Permission;
import com.cnbmtech.cdwpcore.aaa.module.account.model.RoleInfo;
import com.cnbmtech.cdwpcore.aaa.module.account.model.RolePermission;
import com.cnbmtech.cdwpcore.aaa.module.account.model.UserRole;

@Service
public class UrpService {
	
	@Autowired
	RoleInfoRepository roleInfoRepository;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	RolePermissionRepository rolePermissionRepository;
	
	/**
	 * 获取用户下角色
	 * @param userId
	 * @return
	 */
	public List<RoleInfo> findRolesByUserId(Long userId) {
		List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
		List<RoleInfo> returnList = new ArrayList<RoleInfo>();
		for (UserRole ur : userRoles) {
			returnList.add(roleInfoRepository.getOne(ur.getRoleId()));
		}
		return returnList;
	}
	
	/**
	 * 获取角色下权限
	 * @param roleId
	 * @return
	 */
	public List<Permission> findPersByRoleId(Long roleId) {
		List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
		List<Permission> returnList = new ArrayList<Permission>();
		for (RolePermission rp : rolePermissions) {
			returnList.add(permissionRepository.getOne(rp.getPerId()));
		}
		return returnList;
	}

	public List<Permission> getJQgridPermissions() {
		List<Permission> all = permissionRepository.findAll();
		Map<Long, Permission> map = all.stream().collect(Collectors.toMap(obj -> obj.getId(), obj -> obj));
		for (Permission per : all) {
			if (per.getPid() != null) {
				int pIndex = all.indexOf(map.get(per.getPid()));
				int sIndex = all.indexOf(map.get(per.getId()));
				if (pIndex < sIndex) {
					Collections.rotate(all.subList(pIndex + 1, sIndex + 1), 1);
				} else {
					Collections.rotate(all.subList(sIndex, pIndex + 1), -1);
				}
			}
		}
		return all;
	}
	
}
