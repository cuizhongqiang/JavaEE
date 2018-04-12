
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
 * @Description: 角色权限关系
 * @author czq
 * @date 2018年2月7日
 */
@Entity
public final class RolePermission {
	@Id
	@GeneratedValue
	Long id;
	Long roleId;
	Long perId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

}
