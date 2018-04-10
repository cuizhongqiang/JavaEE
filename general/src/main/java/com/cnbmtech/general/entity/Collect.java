package com.cnbmtech.general.entity;

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

/**
 * 会议收集
 */
@Entity
@Table(name = "meeting_collect")
@DynamicUpdate @DynamicInsert
public class Collect extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8758646617540770496L;
	
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
	 * 类型
	 * 1.text,2.checkbox,3.select
	 */
	@Column
	private String type;
	
	/**
	 * 字典编码
	 */
	@Column
	private String dictCode;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "collect")
	private List<TemplateCollect> templateCollect = new ArrayList<TemplateCollect>();
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public List<TemplateCollect> getTemplateCollect() {
		return templateCollect;
	}

	public void setTemplateCollect(List<TemplateCollect> templateCollect) {
		this.templateCollect = templateCollect;
	}
	
}
