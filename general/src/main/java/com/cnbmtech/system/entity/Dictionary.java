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

@Entity
@Table(name = "sys_dictionary")
@DynamicUpdate @DynamicInsert
public class Dictionary extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8618215001740118791L;
	
	/**
	 * 父id
	 */
	@Column
	private Long pid;
	
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
	 * 值
	 */
	@Column
	private String value;
	
	/**
	 * 顺序
	 */
	@Column
	private Integer sort;
	
	/**
	 * 备注
	 */
	@Column
	private String remark;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	private List<Dictionary> children = new ArrayList<Dictionary>();

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Dictionary> getChildren() {
		return children;
	}

	public void setChildren(List<Dictionary> children) {
		this.children = children;
	}

}
