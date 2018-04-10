package com.cbmie.webservice.login;

public class UserObject {
	private Long id;
	private String username;// 登录名称
	private String password;
	private String usernameCN;// 中文名字
	// private List<ModuleObject> modules;//模块

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getUsernameCN() {
		return usernameCN;
	}

	public void setUsernameCN(String usernameCN) {
		this.usernameCN = usernameCN;
	}
	// public List<ModuleObject> getModules() {
	// return modules;
	// }
	// public void setModules(List<ModuleObject> modules) {
	// this.modules = modules;
	// }

}
