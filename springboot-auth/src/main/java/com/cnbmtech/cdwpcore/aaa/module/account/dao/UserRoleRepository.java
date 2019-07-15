package com.cnbmtech.cdwpcore.aaa.module.account.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnbmtech.cdwpcore.aaa.module.account.model.UserRole;

/**
 * @ClassName:
 * @Description: 用户角色关联jpa
 * @author czq
 * @date 2018年2月7日
 */
@RepositoryRestResource
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	List<UserRole> findByUserId(final @Param("userId") @RequestParam("userId")  Long userId);
	
	List<UserRole> findByRoleId(final @Param("roleId") @RequestParam("roleId")  Long roleId);
	
}


