package com.cbmie.genMac.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cbmie.common.entity.BaseEntity;

@Entity
@Table(name = "goods_child")
public class GoodsChild extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 实体字段
	 */
	private Long pid;
	private String name;
	private Integer count;
	private String type;
	private String state = "生效";
	
	@Column(name = "PID")
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "COUNT", nullable = false)
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "TYPE", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "STATE", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}