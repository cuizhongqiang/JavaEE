package com.cbmie.activiti.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ActivitiIdentityService {
	
	@Autowired
	IdentityService identityService;
	
	/**
	 * 用户与actitviti用户同步
	 * loginName，name，email必填
	 * */
	@Transactional(readOnly=false)
	public void synActivitiUser(com.cbmie.system.entity.User appUser){
		List<User> list = identityService.createUserQuery().userId(appUser.getLoginName()).list();
		User curUser = null;
		if(list==null || list.size()==0){
			curUser = identityService.newUser(appUser.getLoginName());
		}else{
			curUser = list.get(0);
		}
		curUser.setFirstName(appUser.getName().substring(0, 1));
		curUser.setLastName(appUser.getName().substring(1));
		curUser.setEmail(appUser.getEmail());
		identityService.saveUser(curUser);
	}
	
	/**
	 * 删除actitviti用户
	 * userId为流程用户id，系统中为登录帐号
	 * */
	@Transactional(readOnly=false)
	public void deleteActivitiUser(String userId){
		identityService.deleteUser(userId);
	}
	
	/**
	 * 增加用户组关系
	 * userId为流程用户id，系统中为登录帐号
	 * 
	 * */
	@Transactional(readOnly=false)
	public void createActivitiMembership(String userId, String groupId){
		identityService.createMembership(userId, groupId);
	}
	
	/**
	 * 删除用户组关系
	 * userId为流程用户id，系统中为登录帐号
	 * 
	 * */
	@Transactional(readOnly=false)
	public void deleteActivitiMembership(String userId, String groupId){
		identityService.deleteMembership(userId, groupId);
	}
	
	/**
	 * 同步用户组
	 * roleCode为系统角色code，必填
	 * 
	 * */
	@Transactional(readOnly=false)
	public void synActivitiGroup(String roleCode,String roleName){
		Group group = identityService.newGroup(roleCode);
		group.setName(roleName);
		group.setType("assignment");
		identityService.saveGroup(group);
	}
	
	/**
	 * 删除用户组关系
	 * userId为流程用户id，系统中为登录帐号
	 * 
	 * */
	@Transactional(readOnly=false)
	public void deleteActivitiGroup(String roleCode){
		identityService.deleteGroup(roleCode);
	}
}
