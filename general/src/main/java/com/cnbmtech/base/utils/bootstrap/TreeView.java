package com.cnbmtech.base.utils.bootstrap;

import java.util.List;

public class TreeView {
	
	private Long id;
	
	private String text;
	
	private String icon;
	
	private String href = "#";
	
	private State state = new State();
	
	private String[] tags;
	
	private List<TreeView> nodes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public List<TreeView> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeView> nodes) {
		this.nodes = nodes;
	}

}
