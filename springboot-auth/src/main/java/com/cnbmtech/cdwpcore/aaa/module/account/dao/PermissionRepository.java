package com.cnbmtech.cdwpcore.aaa.module.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cnbmtech.cdwpcore.aaa.module.account.model.Permission;

/**
 * @ClassName:
 * @Description: 权限jpa
 * @author czq
 * @date 2018年2月7日
 */
@RepositoryRestResource
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
}


