package com.cbmie.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.DictChildDao;
import com.cbmie.system.dao.DictMainDao;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 字典子表service
 */
@Service
@Transactional
public class DictChildService extends BaseService<DictChild, Integer> {
	
	@Autowired
	private DictChildDao dictChildDao;
	
	@Autowired
	private DictMainDao dictMainDao;

	@Override
	public HibernateDao<DictChild, Integer> getEntityDao() {
		return dictChildDao;
	}
	
	
	/**
	 * 根据type获取客户类型所有名称并组成字符串返回
	 * @param types
	 * @return
	 */
	public String findAllTypeNames(String types,String dict){
		DictMain dictMain = dictMainDao.findByCode(dict);
		String[] startType = types.split(",");
		List<String> endTypes = new ArrayList<String>();
		if(startType.length>0){
			for(int i=0;i<startType.length;i++){
					DictChild dictChild = dictChildDao.getKHLX(startType[i],dictMain.getId());
					endTypes.add(i, dictChild.getName());
			}
		}
		return (String)endTypes.toString().replace("[", "").replace("]", "");
	}
	
	
	/**
	 * 根据pid和id查询字典子表
	 * @param mainId pid
	 * @param value id
	 * @return
	 */
	public DictChild getDictName(Integer pid, Integer id){
		return dictChildDao.getDictName(pid, id);
	}
	
	
	public void save(DictMain dictMain, String dictChildJson) {
		// 转成标准的json字符串
		dictChildJson = StringEscapeUtils.unescapeHtml4(dictChildJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<DictChild> dictChildList = new ArrayList<DictChild>();
		try {
			JsonNode jsonNode = objectMapper.readTree(dictChildJson);
			for (JsonNode jn : jsonNode) {
				DictChild dictChild = objectMapper.readValue(jn.toString(), DictChild.class);
				dictChildList.add(dictChild);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取dictMain中dictChild持久化对象
		List<DictChild> dataDictChildList = dictMain.getDictChild();
		// 将数据库数据放入映射
		Map<Integer, DictChild> dataDictChildMap = new HashMap<Integer, DictChild>(); 
		for (DictChild dataDictChild : dataDictChildList) {
			dataDictChildMap.put(dataDictChild.getId(), dataDictChild);
		}
		// 排除没有发生改变的
		for (DictChild dataDictChild : dataDictChildList) {
			if (dictChildList.contains(dataDictChild)) {
				dictChildList.remove(dataDictChild);
				dataDictChildMap.remove(dataDictChild.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (DictChild dictChild : dictChildList) {
			if (dictChild.getId() == null) {
				insert(dictMain.getId(), dictChild, dataDictChildList); // 新增 
			}
			DictChild dataDictChild = dataDictChildMap.get(dictChild.getId());
			if (dataDictChild != null) {
				update(dataDictChild, dictChild); // 修改
				dataDictChildMap.remove(dictChild.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Integer, DictChild> entry : dataDictChildMap.entrySet()) {
			dataDictChildList.remove(entry.getValue());
			dictChildDao.delete(entry.getKey());
		}
	}

	private void update(DictChild dataDictChild, DictChild dictChild) {
		dataDictChild.setName(dictChild.getName());
		dataDictChild.setValue(dictChild.getValue());
		dataDictChild.setCode(dictChild.getCode());
		dataDictChild.setScode(dictChild.getScode());
		dataDictChild.setOrderNum(dictChild.getOrderNum());
		dataDictChild.setRemark(dictChild.getRemark());
	}
	
	private void insert(Integer pid, DictChild dictChild, List<DictChild> dataDictChildList) {
		dictChild.setPid(pid);
		dataDictChildList.add(dictChild);
	}
	
}
