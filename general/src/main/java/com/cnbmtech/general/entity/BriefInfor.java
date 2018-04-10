package com.cnbmtech.general.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cnbmtech.base.entity.BaseEntity;

/**
 * 简介信息
 * @author wxz
 */
@Entity
@Table(name = "brief_infor")
@DynamicUpdate @DynamicInsert
public class BriefInfor extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8094049181506024334L;
	
	/**
	 * 类型
	 * 公司-0，项目-1，会议-2
	 */
	@Column
	private Integer type;
	
	/**
	 * 标题
	 */
	@Column
	private String title;
	
	/**
	 * 内容
	 * 
	 */
	@Column(columnDefinition = "varchar(500)")
	private String articleContent;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	
	

}
