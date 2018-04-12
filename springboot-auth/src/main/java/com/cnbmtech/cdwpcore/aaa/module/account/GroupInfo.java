
    /**  
    * @Title: GroupInfo.java
    * @Package com.cnbmtech.cdwpcore.aaa.module.account
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月1日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.account;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
    * @ClassName: GroupInfo
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月1日
    *
    */
@Entity
public final class GroupInfo {
	@Id
	@GeneratedValue
	Long id;
	@ApiModelProperty(value = "组织机名称")
	String groupName;
	@ApiModelProperty(value = "组织机构值")
	String groupValue;
	@ApiModelProperty(value = "使用状态")
	Integer userType; 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
