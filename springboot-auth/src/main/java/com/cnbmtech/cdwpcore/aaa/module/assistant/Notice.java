
/**  
* @Title: Notice.java
* @Package com.cnbmtech.cdwpcore.aaa.module.assistant
* @Description: TODO
* @author zhengangwu
* @date 2018年2月11日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.assistant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.cnbmtech.cdwpcore.aaa.module.audit.AbsAudit;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: Notice
 * @Description: TODO
 * @author zhengangwu
 * @date 2018年2月11日
 *
 */
@Entity
public class Notice extends AbsAudit {
	@Id
	String name;
	String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Enumerated(EnumType.STRING) // 保存字面值到数据库
	@ApiModelProperty(value = "类别")
	NoticeCatalog catalog;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NoticeCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(NoticeCatalog catalog) {
		this.catalog = catalog;
	}

}
