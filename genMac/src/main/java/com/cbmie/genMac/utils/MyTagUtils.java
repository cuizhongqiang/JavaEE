package com.cbmie.genMac.utils;

import java.util.List;

import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.baseinfo.service.AffiBaseInfoService;
import com.cbmie.system.entity.Permission;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.DictChildService;
import com.cbmie.system.service.PermissionService;
import com.cbmie.system.service.UserService;

/**
 * 自定义标签工具类
 * @author 
 * @version 
 */
public class MyTagUtils {
	
	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	
	private static AffiBaseInfoService affiBaseInfoService = SpringContextHolder.getBean(AffiBaseInfoService.class);

	private static DictChildService dictChildService = SpringContextHolder.getBean(DictChildService.class);
	
	private static PermissionService permissionService = SpringContextHolder.getBean(PermissionService.class);
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public static User getUserByLoginName(String loginName) {
		return userService.getUser(loginName);
	}
	
	/**
	 * 判断用户是否有指定权限
	 * @param loginName 登录名
	 * @param permCode 权限编码
	 * @return
	 */
	public static Boolean shiro(String loginName, String permCode) {
		User user = userService.getUser(loginName);
		List<Permission> pList = permissionService.getPermissions(user.getId());
		for (Permission permission : pList) {
			if (permCode.equals(permission.getPermCode())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据id获取关联单位
	 * @param id
	 * @return
	 */
	public static AffiBaseInfo getAffiliatesById(String id) {
		return affiBaseInfoService.get(Long.valueOf(id));
	}
	
	/**
	 * 根据多选的字符串和字典值返回 多个字符串对应的值
	 * @param types 多选对应的字符串
	 * @param dict 字典值
	 * @return
	 */
	public static String getDict(String types, String dict) {
		return dictChildService.findAllTypeNames(types,dict);
	}
}
