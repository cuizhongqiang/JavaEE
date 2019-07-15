package com.cbmie.webservice.login;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService
public class MobileLogin {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@WebMethod
	public UserObject mobLogin(String username, String password){
		UserObject userObject = new UserObject();
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}
		if(subject.isAuthenticated()||subject.isRemembered()){
			userObject.setUsername(username);
			userObject.setPassword(password);
			return userObject;
		}
		return null;
	}
	
}
