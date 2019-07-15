package com.cbmie.system.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbmie.common.utils.Global;

public class LoginFilter implements Filter {
	
	private String adminPath;

	public void init(FilterConfig filterConfig) throws ServletException {
		adminPath = Global.getAdminPath();
	}

	public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequestt;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String currentURL = request.getRequestURI();//取得根目录所对应的绝对路径:
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1));

		if (targetURL.indexOf(".") == -1 
				&& !targetURL.endsWith(adminPath) 
				&& !targetURL.startsWith("/webservice")
				&& !targetURL.contains(adminPath + "/login") && !targetURL.endsWith(adminPath + "/logout")
				&& (request.getSession(false) == null ? true : request.getSession().getAttribute("user") == null)) {
			PrintWriter writer = response.getWriter();
			String ajaxSubmit = request.getHeader("X-Requested-With");
			if (ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
				return;
			} else if (targetURL.startsWith("/mobile")) {
				writer.write("<script type=\"text/javascript\"><![CDATA[window.open(\"res:page/login.xhtml\")]]></script>");
				writer.flush();
				writer.close();
				return;
			} else {
				writer.write("<script>window.top.location.replace('" + request.getContextPath() + adminPath + "/login')</script>");
				writer.flush();
				writer.close();
				return;
			}
		}
		chain.doFilter(servletRequestt, servletResponse);
	}

	public void destroy() {

	}
}
