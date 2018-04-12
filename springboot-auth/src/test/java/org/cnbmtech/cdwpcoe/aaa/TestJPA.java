
    /**  
    * @Title: TestJPA.java
    * @Package org.cnbmtech.cdwpcoe.aaa
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月5日
    * @version V1.0  
    */
    
package org.cnbmtech.cdwpcoe.aaa;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cnbmtech.cdwpcore.aaa.Application;
import com.cnbmtech.cdwpcore.aaa.module.manager.DataDictionaryRepository;

/**
    * @ClassName: TestJPA
    * @Description: TODO
    * @author zhengangwu
    * @date 2018年2月5日
    */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestJPA {
	@Autowired
	private DataDictionaryRepository dataDictionaryRepository;
	@Test
	public void dataDictionaryRepositoryTest() {
		final List<Map<String,?>> r = dataDictionaryRepository.countListCatalogs();
		for(Map<String,?> a:r){
			for(String key:a.keySet()){
				System.out.println(a.get(key).getClass());
			}
		}
	}	
}
