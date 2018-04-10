package com.cbmie.system.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Role;


/**
 * 角色DAO
 */
@Repository
public class RoleDao extends HibernateDao<Role, Integer>{

}
