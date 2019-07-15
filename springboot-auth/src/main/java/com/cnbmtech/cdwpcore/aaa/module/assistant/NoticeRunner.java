package com.cnbmtech.cdwpcore.aaa.module.assistant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
@Order(value=2)
public class NoticeRunner implements ApplicationRunner {
	@Autowired
	NoticeRepository repo;
	
    @Override
    public void run(ApplicationArguments args) throws Exception {
    	System.out.println("===MyApplicationRunner===\n"+ Arrays.asList(args.getSourceArgs()));
        System.out.println("===getOptionNames========\n"+args.getOptionNames());
    	
    	final Random rand = new Random();
    	
    	final List<Notice> tasks = new ArrayList<Notice>();
    	for(int i = 0;i<10;i++){
    		final Map<String,String> task = new HashMap<String,String>();
    		task.put("name","task "+i);
    		task.put("content","taskcontent" +i);
    		task.put("completeness",String.valueOf(rand.nextInt(100)));
    		final String content = JSON.toJSONString(task);
    		final Notice notice = new Notice();
    		notice.name = task.get("name");
    		notice.content = content;
    		notice.catalog = NoticeCatalog.Task;
    		tasks.add(notice);
    	}
    	
		repo.save(tasks);
    }
}