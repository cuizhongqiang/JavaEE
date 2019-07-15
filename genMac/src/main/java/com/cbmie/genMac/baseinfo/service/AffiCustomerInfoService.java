package com.cbmie.genMac.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringEscapeUtils;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.AffiCustomerInfoDao;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.baseinfo.entity.AffiCustomerInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__客户评审service
 */
@Service
@Transactional
public class AffiCustomerInfoService extends BaseService<AffiCustomerInfo, Long> {

	@Autowired
	private AffiCustomerInfoDao affiCustomerInfoDao;

	@Override
	public HibernateDao<AffiCustomerInfo, Long> getEntityDao() {
		return affiCustomerInfoDao;
	}
	
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public List<AffiCustomerInfo> getByParentId(String parentId) {
		List<AffiCustomerInfo> affiCustomerInfo = affiCustomerInfoDao.findBy("parentId", parentId);
		return affiCustomerInfo;
	}
	
	/**
	 * 保存客户评审子表
	 * @param affiBaseInfo
	 * @param affiCustomerJson
	 */
	public void save(AffiBaseInfo affiBaseInfo, String affiCustomerJson) {
		// 转成标准的json字符串
		affiCustomerJson = StringEscapeUtils.unescapeHtml4(affiCustomerJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AffiCustomerInfo> affiCustomerInfoList = new ArrayList<AffiCustomerInfo>();
		try {
			JsonNode jsonNode = objectMapper.readTree(affiCustomerJson);
			for (JsonNode jn : jsonNode) {
				AffiCustomerInfo affiBankInfo = objectMapper.readValue(jn.toString(), AffiCustomerInfo.class);
				affiCustomerInfoList.add(affiBankInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取goods中goodsChild持久化对象
		List<AffiCustomerInfo> dataAffiCustomerInfoList = affiBaseInfo.getAffiCustomerInfo();
		// 将数据库数据放入映射
		Map<Long, AffiCustomerInfo> dataAffiCustomerMap = new HashMap<Long, AffiCustomerInfo>(); 
		for (AffiCustomerInfo dataAffiCustomerInfo : dataAffiCustomerInfoList) {
			dataAffiCustomerMap.put(dataAffiCustomerInfo.getId(), dataAffiCustomerInfo);
		}
		// 排除没有发生改变的
		for (AffiCustomerInfo dataAffiCustomerInfo : dataAffiCustomerInfoList) {
			if (affiCustomerInfoList.contains(dataAffiCustomerInfo)) {
				affiCustomerInfoList.remove(dataAffiCustomerInfo);
				dataAffiCustomerMap.remove(dataAffiCustomerInfo.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AffiCustomerInfo affiCustomerInfo : affiCustomerInfoList) {
			if (affiCustomerInfo.getId() == null) {
				insert(affiBaseInfo.getId(), affiCustomerInfo, dataAffiCustomerInfoList); // 新增 
			}
			AffiCustomerInfo dataAffiCustomerInfo = dataAffiCustomerMap.get(affiCustomerInfo.getId());
			if (dataAffiCustomerInfo != null) {
				update(dataAffiCustomerInfo, affiCustomerInfo); // 修改
				dataAffiCustomerMap.remove(affiCustomerInfo.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AffiCustomerInfo> entry : dataAffiCustomerMap.entrySet()) {
			dataAffiCustomerInfoList.remove(entry.getValue());
			affiCustomerInfoDao.delete(entry.getKey());
		}
	}

	private void update(AffiCustomerInfo dataAffiCustomerInfo, AffiCustomerInfo affiCustomerInfo) {
		dataAffiCustomerInfo.setCreditLine(affiCustomerInfo.getCreditLine());
		dataAffiCustomerInfo.setCheckStartTime(affiCustomerInfo.getCheckStartTime());
		dataAffiCustomerInfo.setCheckEndTime(affiCustomerInfo.getCheckEndTime());
		dataAffiCustomerInfo.setCustomerAndConditions(affiCustomerInfo.getCustomerAndConditions());
	}
	
	private void insert(Long parentId, AffiCustomerInfo affiCustomerInfo, List<AffiCustomerInfo> dataAffiCustomerInfoList) {
		affiCustomerInfo.setParentId(Long.toString(parentId));
		dataAffiCustomerInfoList.add(affiCustomerInfo);
	}
	
}
