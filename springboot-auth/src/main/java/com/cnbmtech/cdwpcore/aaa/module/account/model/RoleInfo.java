
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
 * @Description: 角色
 * @author czq
 * @date 2018年2月7日
 */
@Entity
public final class RoleInfo {
	@Id
	@GeneratedValue
	Long id;
	String roleName;
	String roleCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
