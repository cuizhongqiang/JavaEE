package com.cbmie.webservice.todoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;

@WebService
public class MobileTodoList  {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ActivitiService activitiService;
	@Autowired
	UserService userService;
	
	@WebMethod
	public List<TaskObject> getTodoList(String username, String lastId){
		List<TaskObject> list_obj = new ArrayList<TaskObject>();
		
		try {/*
			//设置用户session
			User user = userService.getUser(username);
			Session session =SecurityUtils.getSubject().getSession();
			session.setAttribute("user", user);
			
			List<Map<String, Object>> list = activitiService.getTodoList();
			List<Map<String, Object>> list_toshow = new ArrayList<Map<String, Object>>();
			list_toshow.addAll(list);
			
			for(Map<String, Object> map : list){
				if(!"".equals(lastId)){
					if(!((String)map.get("id")).equals(lastId)){
						list_toshow.remove(map);
						continue;
					}
					list_toshow.remove(map);
				}
				if(list_toshow.size()>5){
					for(int i=0; i<5; i++){
						TaskObject obj = new TaskObject();
						obj.setId((String)list_toshow.get(i).get("id"));
						obj.setStatus((String)list_toshow.get(i).get("status"));
						obj.setBusinessInfo((String)list_toshow.get(i).get("businessInfo"));
						obj.setName((String)list_toshow.get(i).get("name"));
						obj.setKey((String)list_toshow.get(i).get("key"));
						obj.setPid((String)list_toshow.get(i).get("pid"));
						obj.setCreateTime((String)list_toshow.get(i).get("createTime"));
						obj.setBusinessKey((String)list_toshow.get(i).get("businessKey"));
						list_obj.add(obj);
					}
				} else {
					for(int i=0; i<list_toshow.size(); i++){
						TaskObject obj = new TaskObject();
						obj.setId((String)list_toshow.get(i).get("id"));
						obj.setStatus((String)list_toshow.get(i).get("status"));
						obj.setBusinessInfo((String)list_toshow.get(i).get("businessInfo"));
						obj.setName((String)list_toshow.get(i).get("name"));
						obj.setKey((String)list_toshow.get(i).get("key"));
						obj.setPid((String)list_toshow.get(i).get("pid"));
						obj.setCreateTime((String)list_toshow.get(i).get("createTime"));
						obj.setBusinessKey((String)list_toshow.get(i).get("businessKey"));
						list_obj.add(obj);
					}
				}
				break;
			}
		*/} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(),e);
		}
		
		return list_obj;
	}

}
