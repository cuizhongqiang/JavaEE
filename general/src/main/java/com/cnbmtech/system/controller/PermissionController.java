package com.cnbmtech.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.utils.bootstrap.TreeView;
import com.cnbmtech.system.entity.Permission;
import com.cnbmtech.system.service.PermissionService;

@Controller
@RequestMapping(value = "permission")
public class PermissionController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "system/permissionList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", permissionService.getAll());
		return map;
	}
	
	@RequestMapping(value = "toCreate/{parentId}")
	public String toUpdate(@PathVariable("parentId") Long parentId, Model model) {
		Permission permission = new Permission();
		if (parentId > 0) {
			permission.setParentId(parentId);
			Permission parentPermission = permissionService.get(parentId);
			permission.setLevel(parentPermission.getLevel() + 1);
		} else {
			permission.setLevel(1);
		}
		model.addAttribute("action", "create");
		model.addAttribute("permission", permission);
		return "system/permissionForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		Permission permission = permissionService.get(id);
		model.addAttribute("permission", permission);
		return "system/permissionForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("permission") @RequestBody Permission permission) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				permission.setCreateDate(currentDate);
			} else {
				permission.setUpdateDate(currentDate);
			}
			permissionService.save(permission);
		}catch(Exception ex){
			logger.error("save permission()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("permission", permissionService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		permissionService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "updateStatus/{id}")
	@ResponseBody
	public String updateStatus(@PathVariable("id") Long id) {
		Permission permission = permissionService.get(id);
		if (permission.getStatus() == 0) {
			permission.setStatus(1);
		} else {
			permission.setStatus(0);
		}
		permissionService.update(permission);
		return "success";
	}
	
	/**
	 * 右侧菜单
	 */
	@RequestMapping(value = "getMenu/{userId}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<TreeView> getMenu(@PathVariable("userId") Long userId) {
		return permissionService.getMenu(userId);
	}
	
}
