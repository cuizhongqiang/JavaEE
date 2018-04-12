package com.cnbmtech.cdwpcore.aaa.module.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnbmtech.cdwpcore.aaa.module.account.dao.PermissionRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.Permission;
import com.cnbmtech.cdwpcore.aaa.module.account.service.UrpService;
import com.cnbmtech.cdwpcore.aaa.msg.ResponseBean;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
	
	@Autowired
	PermissionRepository permissionRepository;
	
	@Autowired
	UrpService urpService;
	
	@ApiOperation("获取权限列表")
	@RequestMapping(value = "/jqGridList", method = RequestMethod.GET)
	public Map<String, Object> getList() {
		List<Permission> list = urpService.getJQgridPermissions();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		return map;
	}
	
	@ApiOperation("权限修改")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseBean update(@Valid @ModelAttribute("permission") @RequestBody Permission permission) {
		permissionRepository.save(permission);
		return new ResponseBean(200, "success", permission);
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("permission", permissionRepository.findOne(id));
		}
	}
	
}
