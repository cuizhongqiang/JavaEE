
    /**  
    * @Title: SpringUtil.java
    * @Package com.cnbmtech.cdwpcore.sapclient.utils
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月7日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
    * @ClassName: SpringUtil
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月7日
    *
    */
@Component
public final class SpringUtil implements ApplicationContextAware{
	@Autowired
    private static ApplicationContext applicationContext;  
    
    public void setApplicationContext(final ApplicationContext applicationContext){  
        if(SpringUtil.applicationContext == null){  
            SpringUtil.applicationContext  = applicationContext;  
        }  
  
    }  
  
    //获取applicationContext  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    //通过name获取 Bean.  
    public static Object getBean(final String name){  
        return getApplicationContext().getBean(name);  
  
    }
  
    //通过class获取Bean.  
    public static <T> T getBean(final Class<T> clazz){  
        return getApplicationContext().getBean(clazz);  
    }  
  
    //通过name,以及Clazz返回指定的Bean  
    public static <T> T getBean(final String name,final Class<T> clazz){  
        return getApplicationContext().getBean(name, clazz);  
    }
}
