package com.cnbmtech.cdwpcore.aaa.module.account.service;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.alibaba.fastjson.JSON;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;
import com.cnbmtech.cdwpcore.aaa.module.account.model.Permission;
import com.cnbmtech.cdwpcore.aaa.module.account.model.RoleInfo;

@Component
public class JPARealm extends AuthorizingRealm {

	@Autowired
	AuthUserRepository authUserRepository;
	
	@Autowired
	UrpService urpService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		// 将token转换成UsernamePasswordToken
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		final String username = upToken.getUsername();
		final String password = new String(upToken.getPassword());

		System.out.println(JSON.toJSONString(token));
		
		final AuthUser jpauser = authUserRepository.findByUsername(username);
		
		if (jpauser == null) {
			throw new UnknownAccountException(); // 如果用户名错误
		}

		if (!jpauser.getUsername().equals(username)) {
			throw new UnknownAccountException(); // 如果用户名错误
		}

		if (!jpauser.getPassword().equals(password)) {
			throw new IncorrectCredentialsException(); // 如果密码错误
		}
		System.out.println(getName() + " succeccfully");
		//登录成功修改最后一次登录时间
		jpauser.setLastLoginTime(new Date());
		authUserRepository.save(jpauser);
		//认证信息的principal一定是基本类型字符串用户名，否则会涉及序列化问题，不要放对象AuthUSer
		//wuzhengang 2018.2.12
		return new SimpleAuthenticationInfo(username, password, getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		final SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//认证信息的principal一定是基本类型字符串用户名，否则会涉及序列化问题，不要放对象AuthUSer
		//wuzhengang 2018.2.12
		final String username = String.valueOf(principals.getPrimaryPrincipal());
		final AuthUser jpauser = authUserRepository.findByUsername(username);
		final List<RoleInfo> roleInfos = urpService.findRolesByUserId(jpauser.getId());
		for (RoleInfo roleInfo : roleInfos) {
			authorizationInfo.addRole(roleInfo.getRoleCode());
			
			List<Permission> permissions = urpService.findPersByRoleId(roleInfo.getId());
			for (Permission permission : permissions) {
				authorizationInfo.addStringPermission(permission.getPerCode());
			}
		}
		
		return authorizationInfo;
	}

}
