package com.cbmie.activiti.entity.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Process {
	
	private String id;
	
	private String form;
	
	private String detail;
	
	private String className;
	
	private List<Parameter> parameter = new ArrayList<Parameter>();
	
	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	@XmlElement
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@XmlElement
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Parameter> getParameter() {
		return parameter;
	}

	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}

}
