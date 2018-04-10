package com.cnbmtech.system.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cnbmtech.base.utils.StringUtils;
import com.cnbmtech.system.entity.Permission;
import com.cnbmtech.system.entity.User;
import com.cnbmtech.system.service.PermissionService;
import com.cnbmtech.system.service.UserService;

/**
 * 用户登录与权限拦截器
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取登录的用户
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/a/login");
			return false;
		}

		// 访问的路径
		String invokeUrl = request.getContextPath() + request.getServletPath();

		// 获取所有的权限
		List<Permission> allPermission = permissionService.allCacheable();
		// 判断当前访问的权限，是否在限制中
		boolean hasPermission = false;
		for (Permission permission : allPermission) {
			if (StringUtils.isNotBlank(permission.getUrl()) && invokeUrl.indexOf(permission.getUrl()) > -1) {
				hasPermission = true;
			}
		}
		// 如果当前访问的权限不在限制中,直接允许通过
		if (!hasPermission) {
			return true;
		}
		// 如果当前访问的权限在限制中则判断是否有访问权限
		List<Permission> userPermission = userService.userPermissionCacheable(user.getId());
		for (Permission permission : userPermission) {
			if (StringUtils.isNotBlank(permission.getUrl()) && invokeUrl.indexOf(permission.getUrl()) > -1) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath() + "/a/noPermission");
		return false;
	}

}
