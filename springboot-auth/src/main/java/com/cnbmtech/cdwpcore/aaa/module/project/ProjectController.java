
    /**  
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月24日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.cnbmtech.cdwpcore.aaa.jwt.model.User;

/**
    * @ClassName: ProjectRepository
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月22日
    *
    */
@RestController
@RequestMapping("/project")
public class ProjectController{
	@Autowired
	ProjectRepository dataDictionaryRepository;
	
	@RequestMapping(value = "/testProjectService", method = RequestMethod.POST)
	public Project testProjectService(@RequestBody Project project) {
		return dataDictionaryRepository.save(project);
	}

}
