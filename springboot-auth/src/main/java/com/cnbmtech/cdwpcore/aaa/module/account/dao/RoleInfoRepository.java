package com.cnbmtech.cdwpcore.aaa.module.account.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cnbmtech.cdwpcore.aaa.module.account.model.RoleInfo;

/**
 * @ClassName:
 * @Description: 角色jpa
 * @author czq
 * @date 2018年2月7日
 */
@RepositoryRestResource
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {
	@Query("select roleInfo.roleName from Permission permission, RolePermission rolePermission, RoleInfo roleInfo where permission.perCode = :perCode and permission.id = rolePermission.perId and rolePermission.roleId = roleInfo.id")
	List<String> findRoleNamesByPermissionCode(@Param("perCode") String perCode);

	@Query("select roleInfo.roleName from AuthUser authUser, UserRole userRole, RoleInfo roleInfo where authUser.username = :username and authUser.id = userRole.userId and userRole.roleId = roleInfo.id")
	List<String> findRoleNamesByUsername(@Param("username") String username);
}
