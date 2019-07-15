
    /**  
    * @Title: DataDictionaryRepository.java
    * @Package com.cnbmtech.cdwpcore.aaa.jpa
    * @author markzgwu
    * @date 2017年12月24日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

/**
    * @ClassName: DataDictionaryRepository
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月22日
    *
    */
@RepositoryRestResource
public interface DataDictionaryRepository extends JpaRepository<DataDictionary, Long>{
	
	DataDictionary[] findByCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select content from DataDictionary where catalog = :catalog order by content")
	String[] findContentByCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select catalog from DataDictionary group by catalog order by catalog")
	String[] listCatalogs();
	
	@Query(value="select new map(catalog as catalog, count(catalog) as count) from DataDictionary group by catalog")
	List<Map<String,?>> countListCatalogs();
	
	@Query(value="select catalog from DataDictionary where catalog Like %:catalog% group by catalog order by catalog")
	String[] listCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select count(catalog) from DataDictionary where catalog = :catalog")
	Long countCatalogs(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select new map(catalog  as catalog, count(catalog) as count) from DataDictionary where catalog like %:catalog% group by catalog order by catalog")
	List<Map<String,?>> countListCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
	
	@Query(value="select count(catalog) from DataDictionary where catalog like %:catalog%")
	Long countCatalogsLikeCatalog(final @Param("catalog") @RequestParam("catalog")  String catalog);
}


