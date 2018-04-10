package com.cbmie.system.utils;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 手机登陆权限异常类
 */
public class PhonePermissionException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public PhonePermissionException() {
		super();
	}

	public PhonePermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PhonePermissionException(String message) {
		super(message);
	}

	public PhonePermissionException(Throwable cause) {
		super(cause);
	}
}