
/**  
* @Title: SupplierController.java
* @Package com.cnbmtech.cdwpcore.aaa.jpa
* @Description: TODO(用一句话描述该文件做什么)
* @author markzgwu
* @date 2017年12月6日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.supplier;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnbmtech.cdwpcore.aaa.module.manager.Supplier;
import com.cnbmtech.cdwpcore.aaa.module.manager.SupplierRepository;
import com.cnbmtech.cdwpcore.aaa.module.sapclient.RESTWapper;

/**
 * @ClassName: SupplierController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author markzgwu
 * @date 2017年12月6日
 *
 */
@RestController
@CacheConfig(cacheNames = "defaultCache")
@RequestMapping(value = { "/rest/supplier" }, method = RequestMethod.GET)
public final class AuthSrvSupplierController {
	@Autowired
	SupplierRepository supplierDao;

	@Cacheable("supplier.query")
	@RequestMapping(value = { "/query" }, method = RequestMethod.GET)
	String query(final String keyword) {
		final Future<Supplier> Supplier = supplierDao.query(keyword);
		String result = JSON.toJSONString("pending");
		if (Supplier.isDone()) {
			result = JSON.toJSONString(Supplier);
		}
		return result;
	}

	@Cacheable("supplier.list")
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	String list() {
		//return JSON.toJSONString(supplierDao.findAll());
		return RESTWapper.getSupplierList();
	}

	@RequestMapping(value = { "/reload" }, method = RequestMethod.GET)
	String reload() {
		supplierDao.deleteAll();
		final String json = RESTWapper.getSupplierList();
		JSONObject jsonObj = JSONObject.parseObject(json);
		System.out.println(jsonObj.keySet());
		String json2 = jsonObj.getString("RESULT");
		// System.out.println(json2);
		List<Supplier> suppliers = JSON.parseArray(json2, Supplier.class);
		supplierDao.save(suppliers);
		String result = JSON.toJSONString(suppliers.size());
		return result;
	}
}
