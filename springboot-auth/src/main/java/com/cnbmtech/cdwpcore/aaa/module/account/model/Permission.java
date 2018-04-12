
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
 * @Description: 权限
 * @author czq
 * @date 2018年2月7日
 */
@Entity
public final class Permission {
	@Id
	@GeneratedValue
	Long id;
	Long pid;//父id
	String perName;//名称
	String perCode;//编码
	int level;//级别
	String perType;//类别
	String perUrl;//路径
	Integer sort;//排序
	int status = 1;//1启用 0禁用
	String description;//描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPerType() {
		return perType;
	}

	public void setPerType(String perType) {
		this.perType = perType;
	}

	public String getPerUrl() {
		return perUrl;
	}

	public void setPerUrl(String perUrl) {
		this.perUrl = perUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((perCode == null) ? 0 : perCode.hashCode());
		result = prime * result + ((perName == null) ? 0 : perName.hashCode());
		result = prime * result + ((perType == null) ? 0 : perType.hashCode());
		result = prime * result + ((perUrl == null) ? 0 : perUrl.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (perCode == null) {
			if (other.perCode != null)
				return false;
		} else if (!perCode.equals(other.perCode))
			return false;
		if (perName == null) {
			if (other.perName != null)
				return false;
		} else if (!perName.equals(other.perName))
			return false;
		if (perType == null) {
			if (other.perType != null)
				return false;
		} else if (!perType.equals(other.perType))
			return false;
		if (perUrl == null) {
			if (other.perUrl != null)
				return false;
		} else if (!perUrl.equals(other.perUrl))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
