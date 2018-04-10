package com.cbmie.system.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cbmie.common.mapper.JsonMapper;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.system.dao.DictMainDao;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * @author 
 * @version 
 */
public class DictUtils {
	
	private static DictMainDao dictDao = SpringContextHolder.getBean(DictMainDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	/**
	 * 获取字典项的名字
	 * @param id 
	 *     字典项的ID值
	 * @param code
	 *     字典项的大类CODE
	 * @param defaultValue
	 *     默认值，如果根据ID和CODE没有查到任何职则返回默认值
	 * @return
	 */
	public static String getDictLabel(String id, String code, String defaultValue){
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(id)){
			for (DictChild dict : getDictList(code)){
				if (id.equals(dict.getId().toString())){
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取多个字典项ID的名称，用“,”号分割
	 * @param ids
	 *     字典项ID，多个用“,”号分割
	 * @param code
	 *     字典项的大类CODE
	 * @param defaultValue
	 *     默认值
	 * @return
	 */
	public static String getDictLabels(String ids, String code, String defaultValue){
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(ids)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(ids, ",")){
				valueList.add(getDictLabel(value, code, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String code, String defaultLabel){
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(label)){
			for (DictChild dict : getDictList(code)){
				if (label.equals(dict.getName())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 根据大类CODE获取字典项，如果缓存中不存在改CODE值将重新查询
	 * @param code
	 * @return
	 */
	public static synchronized List<DictChild> getDictList(String code){
		@SuppressWarnings("unchecked")
		Map<String, List<DictChild>> dictMap = (Map<String, List<DictChild>>)EhCacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (DictMain dictMain : dictDao.findAll()){
				List<DictChild> dictChildList_ = Lists.newArrayList();
				for(DictChild dictChild : dictMain.getDictChild()) {
					//System.out.println(dictChild.getId() + "===" + dictChild.getName());
					dictChildList_.add(dictChild);
				}
				dictMap.put(dictMain.getCode().toLowerCase().trim(), dictChildList_);
			}
			EhCacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<DictChild> dictList = dictMap.get(code.toLowerCase().trim());
//		System.out.println("缓存==================================" + code);
//		for(DictChild dictChild : dictList) {
//			System.out.println("id = " + dictChild.getId() + "; name = " + dictChild.getName());
//		}
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.nonEmptyMapper().toJson(getDictList(type));
	}
	
}
