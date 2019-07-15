package com.cbmie.activiti.entity.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wf")
public class Wf {
	
	private List<Process> process = new ArrayList<Process>();

	public List<Process> getProcess() {
		return process;
	}

	public void setProcess(List<Process> process) {
		this.process = process;
	}

}
