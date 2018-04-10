package com.cnbmtech.base.entity;

import java.util.ArrayList;

public class Result {
	/**
	* 表示图片是否已上传成功。
	*/
	public Boolean success;
	/**
	* 自定义的附加消息。
	*/
	public String msg;
	/**
	* 表示原始图片的保存地址。
	*/
	public String sourceUrl;
	/**
	* 表示所有头像图片的保存地址，该变量为一个数组。
	*/
	@SuppressWarnings("rawtypes")
	public ArrayList avatarUrls;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	@SuppressWarnings("rawtypes")
	public ArrayList getAvatarUrls() {
		return avatarUrls;
	}
	@SuppressWarnings("rawtypes")
	public void setAvatarUrls(ArrayList avatarUrls) {
		this.avatarUrls = avatarUrls;
	}
	
}
