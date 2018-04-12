package com.cnbmtech.cdwpcore.aaa.module.account.dao;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnbmtech.cdwpcore.aaa.module.account.model.RolePermission;

/**
 * @ClassName:
 * @Description: 角色权限jpa
 * @author czq
 * @date 2018年2月7日
 */
@RepositoryRestResource
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>{
	List<RolePermission> findByRoleId(final @Param("roleId") @RequestParam("roleId")  Long roleId);
	List<RolePermission> findByPerId(final @Param("perId") @RequestParam("perId")  Long perId);
}