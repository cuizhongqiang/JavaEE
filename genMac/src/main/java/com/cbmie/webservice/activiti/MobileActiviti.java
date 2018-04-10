package com.cbmie.webservice.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.annotation.WebServlet;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.service.ActivitiService;

@WebServlet
public class MobileActiviti {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ActivitiService activitiService;
	
	@WebMethod
	public List<ActivitiImplObject> findBackPoint(String taskId){
		
		List<ActivityImpl> listActivity = new ArrayList<ActivityImpl>();
		List<ActivitiImplObject> listActivityImplObject = new ArrayList<ActivitiImplObject>();
		
		try{
			/*ActivityImpl ai = activitiService.findReApply(taskId, "reApply");
			listActivity.add(ai);
    		listActivity.addAll(activitiService.findBackAvtivity(taskId));*/
    	} catch (Exception e){
    		logger.error(e.getLocalizedMessage(),e);
    	}
		
		for(ActivityImpl obj : listActivity){
			ActivitiImplObject aio = new ActivitiImplObject();
			aio.setId(obj.getId());
			aio.setName((String)obj.getProperties().get("name"));
			listActivityImplObject.add(aio);
		}
		
		return listActivityImplObject;
	}
	
	@WebMethod
	public List<TraceObject> findTrace(String processInstanceId){
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		List<TraceObject> tracelist = new ArrayList<TraceObject>();
    	/*results = activitiService.getTraceInfo(processInstanceId);
    	
    	for(Map<String, Object> map : results){
    		TraceObject obj = new TraceObject();
    		obj.setId((String)map.get("id"));
    		obj.setAssignee((String)map.get("assignee"));
    		obj.setComments((String)map.get("comments"));
    		
    		tracelist.add(obj);
    	}*/
		
		return tracelist;
	}
	
	

}
