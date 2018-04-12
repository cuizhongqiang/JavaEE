
    /**  
    * @Title: common.java
    * @Package com.cnbmtech.cdwpcore.aaa.module
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月11日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.common;


    /**
    * @ClassName: common
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月11日
    *
    */

public abstract class AbsSupportUser {
	String username;
	String usertype;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
