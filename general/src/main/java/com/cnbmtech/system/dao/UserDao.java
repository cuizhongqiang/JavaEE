package com.cnbmtech.system.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.User;

@Repository
public interface UserDao extends BaseDao<User, Long> {
	
	public User findByUserName(String userName);
	
}
