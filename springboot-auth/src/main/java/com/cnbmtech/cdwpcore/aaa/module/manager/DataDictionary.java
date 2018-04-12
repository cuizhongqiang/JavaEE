
/**  
* @Title: User.java
* @Package com.cnbmtech.cdwpcore.aaa.jpa
* @Description: TODO(用一句话描述该文件做什么)
* @author markzgwu
* @date 2017年12月14日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: User
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author markzgwu
 * @date 2017年12月14日
 *
 */
@Entity
@ExcelTarget("DataDictionary")
@ApiModel
@Table(indexes = { @Index(name = "index_name", columnList = "name", unique = true),
		@Index(name = "index_catalog", columnList = "catalog", unique = false) })
public class DataDictionary {

	@Id
	@GeneratedValue
	@Excel(name = "id", orderNum = "1", mergeVertical = true, isImportField = "id")
	@ApiModelProperty(value = "编号")
	Long id;
	@Excel(name = "name", orderNum = "2", mergeVertical = true, isImportField = "name")
	@ApiModelProperty(value = "名称")
	String name;
	@Excel(name = "catalog", orderNum = "3", mergeVertical = true, isImportField = "catalog")
	@ApiModelProperty(value = "类别")
	String catalog;
	@Excel(name = "content", orderNum = "4", mergeVertical = true, isImportField = "content")
	@ApiModelProperty(value = "内容")
	String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
