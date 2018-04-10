package com.cnbmtech.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.base.utils.bootstrap.TreeView;
import com.cnbmtech.system.dao.RoleDao;
import com.cnbmtech.system.entity.Permission;
import com.cnbmtech.system.entity.Role;
import com.cnbmtech.system.entity.RolePermission;

@Service
public class RoleService extends BaseService<Role, Long> {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public BaseDao<Role, Long> getEntityDao() {
		return roleDao;
	}
	
	/**
	 * 构造权限树
	 */
	public List<TreeView> getTreeView(Long roleId) {
		List<Permission> permissionAll = permissionService.findByStatusOrderBySortAsc(1);
		List<Long> havePermissionIds = getPermissionIdsByRoleId(roleId);
		//以parentId为key构造treeViewMap
		Map<Long, List<TreeView>> treeViewMap = new HashMap<Long, List<TreeView>>();
		for (Permission permission : permissionAll) {
			TreeView treeView = new TreeView();
			treeView.setId(permission.getId());
			treeView.setText(permission.getName());
			if (havePermissionIds.contains(permission.getId())) {//选中
				treeView.getState().setChecked(true);
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
		
		List<TreeView> rootTreeViews = getTreeViewFromMap(treeViewMap);
		return rootTreeViews;
	}
	
	/**
	 * 保存权限
	 */
	public void permissionSave(HttpServletRequest request) {
		String[] permissionIds = request.getParameter("permissionIds").split(",");
		Set<Long> permissionIdSet = new HashSet<Long>();
		for (String permissionId : permissionIds) {
			if (permissionId.trim().length() > 0) {
				permissionIdSet.add(Long.valueOf(permissionId));
			}
		}
		Long roleId = Long.valueOf(request.getParameter("roleId"));
		Role role = get(roleId);
		List<RolePermission> rolePermissions = role.getRolePermissions();
		//处理重复,删除多余
		for (RolePermission rolePermission : rolePermissions) {
			Long permissionId = rolePermission.getPermission().getId();
			if (!permissionIdSet.remove(permissionId)) {
				rolePermissionService.deleteById(rolePermission.getId());
			}
		}
		//新增
		for (Long permissionId : permissionIdSet) {
			Permission permission = permissionService.get(permissionId);
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRole(role);
			rolePermission.setPermission(permission);
			rolePermissionService.save(rolePermission);
		}
	}
	
	public List<TreeView> getTreeViewFromMap(Map<Long, List<TreeView>> treeViewMap) {
		// 构造treeView
		List<TreeView> rootTreeViews = treeViewMap.get(0l);// 根节点
		for (Entry<Long, List<TreeView>> entry : treeViewMap.entrySet()) {
			List<TreeView> treeViews = entry.getValue();
			for (TreeView treeView : treeViews) {
				if (treeViewMap.get(treeView.getId()) != null) {
					treeView.setNodes(treeViewMap.get(treeView.getId()));
				}
			}
		}
		return rootTreeViews;
	}
	
	/**
	 * 根据角色id获取权限id集合
	 */
	public List<Long> getPermissionIdsByRoleId(Long roleId) {
		String sql = "SELECT DISTINCT(sp.id) FROM "
				+ "sys_role sr "
				+ "LEFT JOIN sys_role_permission srp ON sr.id = srp.role_id "
				+ "LEFT JOIN sys_permission sp ON sp.id = srp.permission_id "
				+ "WHERE sr.id = ?";
		return jdbcTemplate.queryForList(sql, Long.class, roleId);
	}

}
