
    /**  
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月24日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
    * @ClassName: ProjectRepository
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月22日
    *
    */
@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Long>{

}


