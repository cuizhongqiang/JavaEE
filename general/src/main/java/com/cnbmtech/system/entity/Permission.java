package com.cnbmtech.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cnbmtech.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sys_permission")
@DynamicUpdate @DynamicInsert
public class Permission extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5063512027260060834L;

	/**
	 * 父id
	 */
	@Column
	private Long parentId;
	
	/**
	 * level级别
	 */
	@Column
	private Integer level;
	
	/**
	 * 类型
	 * M-菜单,F-功能
	 */
	@Column
	private String type;
	
	/**
	 * 名称
	 */
	@Column
	private String name;
	
	/**
	 * 编码
	 */
	@Column
	private String code;
	
	/**
	 * 路径
	 */
	@Column
	private String url;
	
	/**
	 * 图标
	 */
	@Column
	private String icon;
	
	/**
	 * 描述
	 */
	@Column
	private String description;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 状态
	 * 1-有效，0-无效
	 */
	@Column
	private int status = 1;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
	private List<RolePermission> rolePermissions = new ArrayList<RolePermission>();

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}
