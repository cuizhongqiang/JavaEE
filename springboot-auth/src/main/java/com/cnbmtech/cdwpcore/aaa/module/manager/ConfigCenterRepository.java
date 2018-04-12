
    /**  
    * @Title: ConfigCenterRepository.java
    * @Package com.cnbmtech.cdwpcore.aaa.module.manager
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月8日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
* @ClassName: DataDictionaryRepository
* @Description: TODO(这里用一句话描述这个类的作用)
* @author markzgwu
* @date 2017年12月22日
*
*/
@RepositoryRestResource
public interface ConfigCenterRepository extends JpaRepository<ConfigCenter, String>{

}



