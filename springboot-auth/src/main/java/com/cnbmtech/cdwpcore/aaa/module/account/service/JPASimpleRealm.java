package com.cnbmtech.cdwpcore.aaa.module.account.service;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;

//@Component
public class JPASimpleRealm implements Realm{
	
	@Autowired
	AuthUserRepository authUserRepository;
	
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		final String username = (String) token.getPrincipal();
		final String password = new String((char[]) token.getCredentials());

		System.out.println(JSON.toJSONString(token));
		final AuthUser jpauser  = authUserRepository.findByUsername(username);
		
		if(jpauser == null) {
			throw new UnknownAccountException(); // 如果用户名错误
		}
		
		if(!jpauser.getUsername().equals(username)) {
			throw new UnknownAccountException(); // 如果用户名错误
		}
		
        if (!jpauser.getPassword().equals(password)) {
            throw new IncorrectCredentialsException(); // 如果密码错误
        }
        System.out.println(getName()+" succeccfully");
        return new SimpleAuthenticationInfo(username,password,getName());
	}

	@Override
	public String getName() {
		return "JPARealm";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

}
