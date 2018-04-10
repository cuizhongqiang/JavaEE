package com.cbmie;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class MobileLoginTest {

    @Test
	public void testlogin(){
		/*//获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory = 
                new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //得到SecurityManager实例并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录 
		subject.logout(); */
	}

}
