
    /**  
    * @Title: UserDao.java
    * @Package com.cnbmtech.cdwpcore.aaa.jpa
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月14日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

/**
    * @ClassName: UserDao
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月14日
    *
    */

@RepositoryRestResource
public interface GroupInfoRepository extends JpaRepository<GroupInfo, Long>{
	@Query(value="select o from GroupInfo o where groupName=:groupname")
	GroupInfo findByGroupName(final @Param("groupname") @RequestParam("groupname")  String groupname);
	
	@Query(value="select o from GroupInfo o where userType=:userType")
	List<GroupInfo> findByUserType(final @Param("userType") @RequestParam("userType") Integer userType);
	 
	@Query(value="select o from GroupInfo o where groupName Like %:groupName% order by groupName")
	List<GroupInfo> listGroupInfosLikeGroupName(final @Param("groupName") @RequestParam("groupName")  String groupName);
	
	@Query(value="select o from GroupInfo o order by userType")
	List<GroupInfo> listGroupInfos();
	
} 
