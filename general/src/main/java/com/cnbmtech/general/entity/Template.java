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
 * 会议模板
 */
@Entity
@Table(name = "template")
@DynamicUpdate @DynamicInsert
public class Template extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1819007666810602059L;
	
	/**
	 * 标题
	 */
	@Column
	private String title;
	
	/**
	 * 划分渠道
	 * 1-是，0-否
	 */
	@Column
	private int channel = 0;
	
	/**
	 * 状态
	 * 1-启动，0-关闭
	 */
	@Column
	private int status = 1;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "template")
	private List<TemplateCollect> templateCollect = new ArrayList<TemplateCollect>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TemplateCollect> getTemplateCollect() {
		return templateCollect;
	}

	public void setTemplateCollect(List<TemplateCollect> templateCollect) {
		this.templateCollect = templateCollect;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

}
