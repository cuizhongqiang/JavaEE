package com.cbmie.system.utils;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class SysInitBean implements ServletContextAware {

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("~~~~~~~~~~~~~~~~~~~SysInitBean~~~~~~~~~~~~~~~~~~~~");
	}

}
