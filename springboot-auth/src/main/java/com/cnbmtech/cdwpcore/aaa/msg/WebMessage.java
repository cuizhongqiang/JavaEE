package com.cnbmtech.cdwpcore.aaa.msg;

public final class WebMessage {
	String owner;
	Object content;
	String status;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public WebMessage() {
		
	}
	
	public WebMessage(String owner, Object content, String status) {
		super();
		this.owner = owner;
		this.content = content;
		this.status = status;
	}
	
}
