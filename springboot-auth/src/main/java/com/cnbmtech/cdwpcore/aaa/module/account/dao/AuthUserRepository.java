package com.cnbmtech.cdwpcore.aaa.module.account.dao;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;

/**
    * @ClassName: 
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月22日
    *
    */
@RepositoryRestResource
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>{
	
	AuthUser findByUsername(final @Param("username") @RequestParam("username")  String username);
	
	List<AuthUser> findByCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select content from AuthUser where catalog = :catalog order by content")
	String[] findContentByCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select catalog from AuthUser group by catalog order by catalog")
	String[] listCatalogs();
	
	@Query(value="select new map(catalog,count(catalog) as count) from AuthUser group by catalog")
	List<Map<String,Long>> countListCatalogs();
	
	@Query(value="select catalog from AuthUser where catalog Like %:catalog% group by catalog order by catalog")
	String[] listCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select count(catalog) from AuthUser where catalog = :catalog")
	Long countCatalogs(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select new map(catalog,count(catalog) as count) from AuthUser where catalog like %:catalog% group by catalog order by catalog")
	List<Map<String,Long>> countListCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select count(catalog) from AuthUser where catalog like %:catalog%")
	Long countCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select count(0) from AuthUser where username = :username and id != :id")
	Integer uniqueUserName(final @Param("username") @RequestParam("username")  String username, final @Param("id") @RequestParam("id") Long id);
	
}


