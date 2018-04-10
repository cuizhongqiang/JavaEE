package com.cbmie.system.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Permission;
import com.cbmie.system.service.PermissionService;
import com.cbmie.system.utils.UserUtil;

/**
 * 权限controller
 */
@Controller
@RequestMapping("system/permission")
public class PermissionController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "system/permissionList";
	}
	
	/**
	 * 菜单页面
	 */
	@RequestMapping(value="menu",method = RequestMethod.GET)
	public String menuList(){
		return "system/menuList";
	}
	
	/**
	 * 菜单集合(JSON)
	 */
	@RequiresPermissions("sys:perm:menu:view")
	@RequestMapping(value="menu/json",method = RequestMethod.GET)
	@ResponseBody
	public List<Permission>  menuDate(){
		List<Permission> permissionList=permissionService.getMenus();
		return permissionList;
	}
	
	/**
	 * 权限集合(JSON)
	 */
	@RequiresPermissions("sys:perm:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<Permission> getData() {
		List<Permission> permissionList=permissionService.getAll();
		return permissionList;
	}
	
	/**
	 * 权限集合(JSON)
	 */
	@RequiresPermissions("sys:perm:view")
	@RequestMapping(value="all/json",method = RequestMethod.GET)
	@ResponseBody
	public List<Permission> getEffectiveData() {
		List<Permission> permissionList=permissionService.getAllEffectiveP();
		return permissionList;
	}
	
	/**
	 * 获取菜单下的操作
	 */
	@RequiresPermissions("sys:perm:view")
	@RequestMapping("ope/json")
	@ResponseBody
	public Map<String, Object> menuOperationDate(Integer pid){
		List<Permission> menuOperList=permissionService.getMenuOperation(pid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menuOperList);
		map.put("total",menuOperList.size());
		return map;
	}
	
	/**
	 * 当前登录用户的权限集合
	 */
	@RequestMapping("i/json")
	@ResponseBody
	public List<Permission> myPermissionDate() {
		List<Permission> permissionList=permissionService.getMenus(UserUtil.getCurrentUser().getId());
		return permissionList;
	}
	
	/**
	 * 某用户的权限集合
	 */
	@RequiresPermissions("sys:perm:view")
	@RequestMapping("{userId}/json")
	@ResponseBody
	public List<Permission> otherPermissionDate(@PathVariable("userId") Integer userId) {
		List<Permission> permissionList=permissionService.getPermissions(userId);
		return permissionList;
	}
	
	/**
	 * 添加权限跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("permission", new Permission());
		model.addAttribute("action", "create");
		return "system/permissionForm";
	}
	
	/**
	 * 添加菜单跳转
	 */
	@RequestMapping(value = "menu/create", method = RequestMethod.GET)
	public String menuCreateForm(Model model) {
		model.addAttribute("permission", new Permission());
		model.addAttribute("action", "create");
		return "system/menuForm";
	}

	/**
	 * 添加权限/菜单
	 */
	@RequiresPermissions("sys:perm:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Permission permission,Model model) {
		permissionService.save(permission);
		return "success";
	}
	
	/**
	 * 添加菜单基础操作
	 * @param pid
	 * @return
	 */
	@RequiresPermissions("sys:perm:add")
	@RequestMapping(value = "createBase/{pname}/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public String create(@PathVariable("pname") String pname,@PathVariable("pid") Integer pid){
		permissionService.addBaseOpe(pid, pname);
		return "success";
	}

	/**
	 * 修改权限跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("permission", permissionService.get(id));
		model.addAttribute("action", "update");
		return "system/permissionForm";
	}
	
	/**
	 * 修改菜单跳转
	 */
	@RequestMapping(value = "menu/update/{id}", method = RequestMethod.GET)
	public String updateMenuForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("permission", permissionService.get(id));
		model.addAttribute("action", "update");
		return "system/menuForm";
	}

	/**
	 * 修改权限/菜单
	 */
	@RequiresPermissions("sys:perm:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("permission") Permission permission,Model model) {
		permissionService.save(permission);
		return "success";
	}

	/**
	 * 删除权限
	 */
	@RequiresPermissions("sys:perm:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		permissionService.delete(id);
		return "success";
	}
	
	@ModelAttribute
	public void getPermission(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("permission", permissionService.get(id));
		}
	}
}
