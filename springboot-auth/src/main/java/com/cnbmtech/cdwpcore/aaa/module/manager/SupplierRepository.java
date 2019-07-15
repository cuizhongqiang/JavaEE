/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.manager;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path="supplier")
@Transactional
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	@Query(value="select o from Supplier o where o.NAME1 like %:NAME1")
	List<Supplier> findByNAME1(final @Param("NAME1") String NAME1);
	
	@Async
	@Query(value="select o from Supplier o where o.NAME1 like %:keyword%")
	Future<Supplier> query(final @Param("keyword") @RequestParam("keyword") String keyword);
}