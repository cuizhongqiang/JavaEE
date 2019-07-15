
/**  
* @Title: AuthUserService.java
* @Package com.cnbmtech.cdwpcore.aaa.logic.jpa
* @Description: TODO(用一句话描述该文件做什么)
* @author markzgwu
* @date 2017年12月22日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AuthUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author markzgwu
 * @date 2017年12月22日
 *
 */

@Service
public class BatchServiceImpl implements BatchService{

	@Autowired
	EntityManager em;

	public <T> void SaveBatch(final List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			em.merge(entities.get(i));
			if (i % 30 == 0) {
				em.flush();
				em.clear();
			}
		}
	}

}
