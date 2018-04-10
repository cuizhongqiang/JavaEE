package com.cnbmtech.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.base.utils.bootstrap.TreeView;
import com.cnbmtech.system.dao.PermissionDao;
import com.cnbmtech.system.entity.Permission;

@Service
public class PermissionService extends BaseService<Permission, Long> {

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public BaseDao<Permission, Long> getEntityDao() {
		return permissionDao;
	}
	
	/**
	 * 构造菜单树
	 */
	public List<TreeView> getMenu(Long userId) {
		List<Permission> permissions = userService.userPermissionCacheable(userId);
		// 以parentId为key构造treeViewMap
		Map<Long, List<TreeView>> treeViewMap = new TreeMap<Long, List<TreeView>>();
		for (Permission permission : permissions) {
			if (!permission.getType().equals("M")) {
				continue;
			}
			TreeView treeView = new TreeView();
			treeView.setId(permission.getId());
			treeView.setText(permission.getName());
			treeView.setIcon(permission.getIcon());
			if (StringUtils.isNotBlank(permission.getUrl())) {
				treeView.setHref(request.getContextPath() + permission.getUrl());
			}
			Long parentId = permission.getParentId() == null ? 0 : permission.getParentId();
			if (treeViewMap.get(parentId) == null) {
				List<TreeView> treeViewList = new ArrayList<TreeView>();
				treeViewList.add(treeView);
				treeViewMap.put(parentId, treeViewList);
			} else {
				treeViewMap.get(parentId).add(treeView);
			}
		}

		List<TreeView> rootTreeViews = roleService.getTreeViewFromMap(treeViewMap);
		return rootTreeViews;
	}
	
	public List<Permission> findByStatusOrderBySortAsc(int status) {
		return permissionDao.findByStatusOrderBySortAsc(status);
	}
	
	/**
	 * Cacheable：当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回了。
	 */
	@Cacheable(value = "userPermission", key = "'all'")
	public List<Permission> allCacheable() {
		return findByStatusOrderBySortAsc(1);
	}
	
	/**
	 * CachePut：这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中。
	 */
	@CachePut(value = "userPermission", key = "'all'")
	public List<Permission> allCachePut() {
		return findByStatusOrderBySortAsc(1);
	}
	
}
