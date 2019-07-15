package com.cnbmtech.cdwpcore.aaa.module.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnbmtech.cdwpcore.aaa.module.account.model.ShiroFilterConfig;

/**
 * @ClassName:
 * @Description: shiro配置jpa
 * @author czq
 * @date 2018年2月7日
 */
@RepositoryRestResource
public interface ShiroFilterConfigRepository extends JpaRepository<ShiroFilterConfig, String>{
	
	// 避免延迟加载
	@Query(value="select ConfigContent from ShiroFilterConfig where ConfigName = :ConfigName")
	String findContentByName(final @Param("ConfigName") @RequestParam("ConfigName")  String ConfigName);
	
}


