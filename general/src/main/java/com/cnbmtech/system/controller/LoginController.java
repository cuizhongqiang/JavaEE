package com.cnbmtech.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.system.entity.User;
import com.cnbmtech.system.service.PermissionService;
import com.cnbmtech.system.service.UserService;

@Controller
@RequestMapping(value = "a")
public class LoginController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("plainPassword");
		
		User user = userService.findByUserName(userName);
		
		if (user == null) {
			return "errorUserName";
		} else {
			if (user.getStatus() == 0) {
				return "disabled";
			}
			boolean pwd = userService.checkPassword(user, password);
			if (pwd) {
				request.getSession().setAttribute("user", user);
			} else {
				return "errorPassword";
			}
		}
		
		//初始缓存
		userService.userPermissionCachePut(user.getId());
		permissionService.allCachePut();
		
		return "success";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "login";
	}
	
	@RequestMapping(value = "index")
	public String index(HttpSession session) {
		return "index";
	}
	
	@RequestMapping(value = "noPermission")
	public String noPermission() {
		return "common/noPermission";
	}

}
