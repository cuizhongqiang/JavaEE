
    /**  
    * @Title: ConfigCenter.java
    * @Package com.cnbmtech.cdwpcore.aaa.module.manager
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月8日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.manager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

/**
    * @ClassName: ConfigCenter
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月8日
    *
    */
@Entity
public class ConfigCenter {
	@Id
	@ApiModelProperty(value = "配置名称")
	String name;
	 //@Enumerated(EnumType.ORDINAL)//保存索引到数据库中  
    @Enumerated(EnumType.STRING)//保存字面值到数据库
	@ApiModelProperty(value = "配置类别")
	ConfigCenterCatalog catalog;
	@ApiModelProperty(value = "配置内容")
	@Column(columnDefinition="TEXT")
	String content;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ConfigCenterCatalog getCatalog() {
		return catalog;
	}
	public void setCatalog(ConfigCenterCatalog catalog) {
		this.catalog = catalog;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
