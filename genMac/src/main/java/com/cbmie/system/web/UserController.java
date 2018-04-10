package com.cbmie.system.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserRoleService;
import com.cbmie.system.service.UserService;

/**
 * 用户controller
 */
@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private OrganizationService organizationService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/userList";
	}

	/**
	 * 获取用户json
	 */
//	@RequiresPermissions("sys:user:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<User> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = userService.search(page, filters);
		return getEasyUIData(page);
	}
	/**
	 */
	@RequestMapping(value="select",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserSelectItem(HttpServletRequest request) {
		List<User> users=userService.getAll();
		return users;
	}
	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "system/userForm";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid User user,@RequestParam Integer newOrganazationId, Model model) {
		user.setOrganization(organizationService.get(newOrganazationId));
		userService.save(user);
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute("action", "update");
		return "system/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user,@RequestParam Integer newOrganazationId,Model model) {
		user.setOrganization(organizationService.get(newOrganazationId));
		userService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "success";
	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userRoleList";
	}
	/**
	 * 弹窗页-用户所在机构
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{userId}/userOrg")
	public String getUserOrg(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userOrgList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/role")
	@ResponseBody
	public String getRoleIdList(@PathVariable("userId") Integer id) {
		List<Integer> list = userRoleService.getRoleIdList(id);
		return userRoleService.idAjaxJson(list);
	}
	/**
	 * 获取用户拥有的机构ID集合
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{userId}/org")
	@ResponseBody
	public String getOrgIdList(@PathVariable("userId") Integer id) {
		User user = userService.get(id);
		Organization org = user.getOrganization();
		List<Integer> list = new ArrayList<Integer>();
		if (org != null) {
			list.add(org.getId());
		}
		return userRoleService.idAjaxJson(list);
	}

	
	/**
	 * 获取用户列表
	 * @param value  角色所对应的id
	 * @return
	 */
	@RequestMapping(value = "getRelativeUser/{value}")
	@ResponseBody
	public List<User> getUser(@PathVariable("value") String value) {
		return userService.getSupplier(value);
	}
	
	
	
	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		userRoleService.updateUserRole(id, userRoleService.getRoleIdList(id),newRoleList);
		return "success";
	}
	/**
	 * 修改用户所在的部门
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:orgUpd")
	@RequestMapping(value = "{id}/updateOrg")
	@ResponseBody
	public String updateUserOrg(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		User user = userService.get(id);
		if (newRoleList != null && newRoleList.size() > 0)
			userService.updateUserOrg(user, organizationService.get(newRoleList.get(0)));
		return "success";
	}

	/**
	 * 修改密码跳转
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.GET)
	public String updatePwdForm(Model model, HttpSession session) {
		model.addAttribute("user", (User) session.getAttribute("user"));
		return "system/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(String oldPassword,@Valid @ModelAttribute @RequestBody User user, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			userService.updatePwd(user);
			session.setAttribute("user", user);
			return "success";
		} else {
			return "fail";
		}

	}
	
	//初始化密码
	@RequestMapping(value = "initPwd/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String initPwd(@PathVariable("id") Integer id) {
		User user = userService.getUser(id);
		user.setPlainPassword("123456");
		userService.updatePwd(user);
		return "success";

	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(String loginName) {
		if (userService.getUser(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * ajax请求校验原密码是否正确
	 * 
	 * @param oldPassword
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "checkPwd")
	@ResponseBody
	public String checkPwd(String oldPassword, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}
	@RequestMapping(value = "getDeptName/{userId}")
	@ResponseBody
	public String getDeptName(@PathVariable("userId") Integer userId){
		String deptName = "";
		User u = userService.get(userId);
		if(u!=null){
			deptName = u.getOrganization()==null?"":u.getOrganization().getOrgName();
		}
		return deptName;
	}

}
