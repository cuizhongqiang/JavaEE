
    /**  
    * @Title: ShiroInit.java
    * @Package com.cnbmtech.cdwpcore.aaa.config
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月8日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
    * @ClassName: ShiroInit
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月8日
    *
    */
@Component
public class ShiroInit implements CommandLineRunner{

	
	    /* (noJavadoc)
	    * 
	    * 
	    * @param arg0
	    * @throws Exception
	    * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	    */
	    
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("ShiroInit()");  
	}

}
