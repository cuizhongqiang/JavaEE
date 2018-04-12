package com.cnbmtech.cdwpcore.aaa.module.account.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class AuthUserLoginForm {
	@NotNull(message = "不能为空")
	@Size(min = 5, max = 30, message = "长度不符合规则")
	String username;

	@NotNull(message = "不能为空")
	@Size(min = 5, max = 30, message = "长度不符合规则")
	String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
	boolean remember;
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
	
}
