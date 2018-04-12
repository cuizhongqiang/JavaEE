
    /**  
    * @Title: GroupInfo.java
    * @Package com.cnbmtech.cdwpcore.aaa.module.account
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月1日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.account.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ClassName:
 * @Description: 用户角色关系
 * @author czq
 * @date 2018年2月7日
 */
@Entity
public final class UserRole {
	@Id
	@GeneratedValue
	Long id;
	Long userId;
	Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
