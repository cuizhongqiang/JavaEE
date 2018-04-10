package com.cbmie.genMac.accessory.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 附件上传下载对应数据库实体
 */
@Entity
@Table(name = "accessory")
public class Accessory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	// 附件accId
	private Long accId;
	// 附件扩展名
	private String accExtension;
	// 附件原名
	private String accRealName;
	// 附件所属父对象
	private String accParentEntity;
	// 附件所属父id
	private String accParentId;
	// 附件保存路径
	private String accSrc;
	// 附件上传人
	private String accAuthor;
	// 下载文件路径
	private String userFile;
	// 下载文件名称
	private String userAccName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACC_ID")
	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	@Column(name = "ACC_EXTENSION")
	public String getAccExtension() {
		return accExtension;
	}

	public void setAccExtension(String accExtension) {
		this.accExtension = accExtension;
	}

	@Column(name = "ACC_REALNAME")
	public String getAccRealName() {
		return accRealName;
	}

	public void setAccRealName(String accRealName) {
		this.accRealName = accRealName;
	}

	@Column(name = "ACC_PARENT_ENTITY")
	public String getAccParentEntity() {
		return accParentEntity;
	}

	public void setAccParentEntity(String accParentEntity) {
		this.accParentEntity = accParentEntity;
	}

	@Column(name = "ACC_PARENT_ID")
	public String getAccParentId() {
		return accParentId;
	}

	public void setAccParentId(String accParentId) {
		this.accParentId = accParentId;
	}

	@Column(name = "ACC_SRC")
	public String getAccSrc() {
		return accSrc;
	}

	public void setAccSrc(String accSrc) {
		this.accSrc = accSrc;
	}

	@Column(name = "ACC_AUTHOR")
	public String getAccAuthor() {
		return accAuthor;
	}

	public void setAccAuthor(String accAuthor) {
		this.accAuthor = accAuthor;
	}

	@Column(name = "USER_FILE")
	public String getUserFile() {
		return userFile;
	}

	public void setUserFile(String userFile) {
		this.userFile = userFile;
	}

	@Column(name = "USER_ACC_NAME")
	public String getUserAccName() {
		return userAccName;
	}

	public void setUserAccName(String userAccName) {
		this.userAccName = userAccName;
	}

}