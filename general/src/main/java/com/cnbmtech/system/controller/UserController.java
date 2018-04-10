package com.cnbmtech.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.base.utils.StringUtils;
import com.cnbmtech.system.entity.Role;
import com.cnbmtech.system.entity.User;
import com.cnbmtech.system.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "system/userList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<User> page = userService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("user", new User());
		return "system/userForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "system/userForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("user") @RequestBody User user) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				user.setCreateDate(currentDate);
				userService.entryptPassword(user);
			} else {
				user.setUpdateDate(currentDate);
			}
			userService.save(user);
		}catch(Exception ex){
			logger.error("save user()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "toUpdatePwd/{id}")
    public String toUpdatePwd(@PathVariable("id") Long id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "system/userUpdatePwdForm";
	}
	
	@RequestMapping(value = "updatePwd/{oldPassword}")
	@ResponseBody
	public String updatePwd(@PathVariable("oldPassword") String oldPassword, @ModelAttribute("user") @RequestBody User user) {
		boolean flag = userService.checkPassword(user, oldPassword);
		if (flag) {
			userService.entryptPassword(user);
			userService.save(user);
		} else {
			return "fail";
		}
		return "success";
	}
	
	@RequestMapping(value = "updateStatus/{id}")
	@ResponseBody
	public String updateStatus(@PathVariable("id") Long id) {
		User user = userService.get(id);
		if (user.getStatus() == 0) {
			user.setStatus(1);
		} else {
			user.setStatus(0);
		}
		userService.update(user);
		return "success";
	}
	
	@RequestMapping(value = "toDistributionRole/{id}")
	public String toDistributionRoleList(@PathVariable("id") Long id, Model model) {
		List<Role> roles = userService.getUserRoles(id);
		List<Long> roleIds = new ArrayList<Long>();
		for (Role role : roles) {
			roleIds.add(role.getId());
		}
		model.addAttribute("roleIds", StringUtils.join(roleIds.toArray(), ","));
		return "system/userDistributionRole";
	}
	
	@RequestMapping(value = "distributionRoleSave")
	@ResponseBody
	public String distributionRoleSave(HttpServletRequest request) {
		userService.distributionRoleSave(request);
		return "success";
	}
	
	@RequestMapping(value = "uniqueUserName")
	@ResponseBody
	public Map<String, Boolean> uniqueUserName(@RequestParam(name = "userName") String userName, @RequestParam(name = "id") Long id) {
		Integer count = userService.uniqueUserName(userName, id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", count == 0);
		return map;
	}
}
