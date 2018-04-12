package com.cnbmtech.cdwpcore.aaa.module.account.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnbmtech.cdwpcore.aaa.module.account.AuthUtils;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.form.AuthMsg;
import com.cnbmtech.cdwpcore.aaa.module.account.form.AuthUserLoginForm;
import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;
import com.cnbmtech.cdwpcore.aaa.msg.ResponseBean;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/authuser")
public class AuthUserController {
	@Autowired
	AuthUserRepository authUserRepository;

	@ApiOperation("获取当前登陆用户名")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public AuthMsg currentUser() {
		final String currentUser = AuthUtils.username();
		String status = "";
		if (StringUtils.isEmpty(currentUser)) {
			status = "Not_Logged_In";
		} else {
			status = "Logged_In";
		}
		return new AuthMsg(currentUser, status);
	}

	@ApiOperation("用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户的姓名", defaultValue = "admin"),
			@ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "用户的密码", defaultValue = "123456"),
			@ApiImplicitParam(paramType = "query", name = "remember", dataType = "boolean", required = true, value = "自动登录", defaultValue = "true") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public AuthMsg login(@Valid AuthUserLoginForm loginform) {
		final Object currentUser = AuthUtils.login(loginform.getUsername(), loginform.getPassword());
		return new AuthMsg(currentUser, "Log_In");
	}

	@ApiOperation("用户注销")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public AuthMsg logout() {
		final String currentUser = AuthUtils.logout();
		return new AuthMsg(currentUser, "Log_Out");
	}

	@ApiOperation("用户会话")
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public AuthMsg session() {
		final Session session = AuthUtils.session();
		return new AuthMsg("SESSION", session);
	}

	@ApiOperation("用户名唯一验证")
	@RequestMapping(value = "/uniqueUserName", method = {RequestMethod.POST,RequestMethod.GET})
	public Map<String, Boolean> uniqueUserName(@RequestParam(name = "username") String username,
			@RequestParam(name = "id") Long id) {
		Integer count = authUserRepository.uniqueUserName(username, id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("valid", count == 0);
		return map;
	}

	@ApiOperation("用户修改")
	@RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
	public ResponseBean update(@Valid @ModelAttribute("authUser") @RequestBody AuthUser authUser) {
		authUserRepository.save(authUser);
		return new ResponseBean(200, "success", authUser);
	}

	@ApiOperation("原密码验证")
	@RequestMapping(value = "/validatePassword", method = {RequestMethod.POST,RequestMethod.GET})
	public Map<String, Boolean> validatePassword(@ModelAttribute("authUser") AuthUser authUser,
			@RequestParam(name = "oldPassword") String oldPassword) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("valid", authUser.getPassword().equals(oldPassword));
		return map;
	}

	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("authUser", authUserRepository.findOne(id));
		}
	}

	@ApiOperation("获取用户流程角色")
	@RequestMapping(value = "/getWorkflowRoles", method = RequestMethod.GET)
	public String getWorkflowRoles(@RequestParam(name = "username") String username) {
		return authUserRepository.findByUsername(username).getWorkflowrole();
	}

	@ApiOperation("更新用户流程角色")
	@RequestMapping(value = "/updateWorkflowRoles", method = {RequestMethod.POST,RequestMethod.GET})
	public AuthMsg updateWorkflowRoles(@RequestParam(name = "username") String username,
			@RequestParam(name = "workflowroles") String workflowroles) {
		AuthUser user = authUserRepository.findByUsername(username);
		user.setWorkflowrole(workflowroles);
		authUserRepository.save(user);
		return new AuthMsg(user, "ok");
	}
}
