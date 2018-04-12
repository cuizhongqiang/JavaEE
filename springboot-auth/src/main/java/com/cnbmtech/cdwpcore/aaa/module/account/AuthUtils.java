package com.cnbmtech.cdwpcore.aaa.module.account;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;

public final class AuthUtils {
	public static String username() {
		final Object user = SecurityUtils.getSubject().getPrincipal();
		return String.valueOf(user);
	}
	
	public static Session session() {
		return SecurityUtils.getSubject().getSession();
	}

	public static String logout() {
		final String username = username();
		if (StringUtils.isNotEmpty(username)) {
			SecurityUtils.getSubject().logout();
		}
		return username;
	}

	public static String login(final String username,final String password) {
		return login(username,password,true);
	}
	
	public static String login(final String username,final String password,final boolean rememberMe) {
		final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		return login(token);
	}

	public static String login(final UsernamePasswordToken token) {
		SecurityUtils.getSubject().login(token);
		final String username = username();
		return username;
	}
}
