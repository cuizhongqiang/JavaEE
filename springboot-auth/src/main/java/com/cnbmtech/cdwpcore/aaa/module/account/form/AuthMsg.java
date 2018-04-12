package com.cnbmtech.cdwpcore.aaa.module.account.form;

public final class AuthMsg {
	Object username;
	Object status;	
	public AuthMsg(Object username, Object status) {
		super();
		this.username = username;
		this.status = status;
	}
	public Object getUsername() {
		return username;
	}
	public void setUsername(Object username) {
		this.username = username;
	}
	public Object getStatus() {
		return status;
	}
	public void setStatus(Object status) {
		this.status = status;
	}
	
}
