package com.cbmie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/** 
* 配置文件载入类  
* @ClassName: BaseSpringTestCase  
* @Description: 要想实现Spring自动注入，必须继承此类 
* @author yusj   
* @date 2014年6月9日 下午3:16:44  
* 
 */  

@Transactional
public class BaseSpringTestCase {
	private static HandlerMapping handlerMapping;        
    private static HandlerAdapter handlerAdapter;
     
    @BeforeClass     
    public static void setUp() {            
        if (handlerMapping == null) {                
            String[] configs = { "classpath*:/applicationContext.xml","classpath*:/spring-mvc.xml" };
            XmlWebApplicationContext context = new XmlWebApplicationContext();                
            context.setConfigLocations(configs);                
            MockServletContext msc = new MockServletContext();                
            context.setServletContext(msc);         
            context.refresh();                
            msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);                
            handlerMapping = (HandlerMapping)context.getBean(RequestMappingHandlerMapping.class);                
            handlerAdapter = (HandlerAdapter)context.getBean(context.getBeanNamesForType(RequestMappingHandlerAdapter.class)[0]);               
        }        
    }  
     
    public ModelAndView excuteController(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecutionChain chain = handlerMapping.getHandler(request);            
        final ModelAndView model = handlerAdapter.handle(request, response, chain.getHandler());            
        return model;
    } 
}
