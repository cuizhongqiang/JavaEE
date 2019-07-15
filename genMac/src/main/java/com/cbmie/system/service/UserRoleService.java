package com.cbmie.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.service.ActivitiIdentityService;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.UserRoleDao;
import com.cbmie.system.entity.Role;
import com.cbmie.system.entity.User;
import com.cbmie.system.entity.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用户角色service
 */
@Service
@Transactional(readOnly = true)
public class UserRoleService extends BaseService<UserRole, Integer> {

	@Autowired
	ActivitiIdentityService activitiIdentityService;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService rolService;

	@Override
	public HibernateDao<UserRole, Integer> getEntityDao() {
		return null;
	}

	/**
	 * 添加修改用户角色
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserRole(Integer userId, List<Integer> oldList,List<Integer> newList) {
		// 是否删除
		for (int i = 0, j = oldList.size(); i < j; i++) {
			if (!newList.contains(oldList.get(i))) {
				activitiIdentityService.deleteActivitiMembership(userService.getUser(userId).getLoginName(), rolService.getRole(oldList.get(i)).getRoleCode());
				userRoleDao.deleteUR(userId, oldList.get(i));
			}
		}

		// 是否添加
		for (int m = 0, n = newList.size(); m < n; m++) {
			if (!oldList.contains(newList.get(m))) {
				userRoleDao.save(getUserRole(userId, newList.get(m)));
				activitiIdentityService.createActivitiMembership(userService.getUser(userId).getLoginName(), rolService.getRole(newList.get(m)).getRoleCode());
			}
		}
	}

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	private UserRole getUserRole(Integer userId, Integer roleId) {
		UserRole ur = new UserRole();
		ur.setUser(new User(userId));
		ur.setRole(new Role(roleId));
		return ur;
	}

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getRoleIdList(Integer userId) {
		return userRoleDao.findRoleIds(userId);
	}
	
	/**
	 * 把id集合转成json
	 */
	public String idAjaxJson(List<Integer> list){
		List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
		for (Integer i : list) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("id", i);
			mapList.add(map);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(mapList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
