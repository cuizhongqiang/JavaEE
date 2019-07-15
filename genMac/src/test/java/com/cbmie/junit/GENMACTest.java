package com.cbmie.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.cbmie.system.service.UserService;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class GENMACTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private UserService userService;

	@Test
	public void quartzTest() throws Exception {
		/*String[] names=applicationContext.getBeanDefinitionNames();
		for(String s:names){
			System.out.println(s);
		}*/
		System.out.println(userService.getAll().size());
	}
}