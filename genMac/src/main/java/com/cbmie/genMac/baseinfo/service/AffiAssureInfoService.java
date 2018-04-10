package com.cbmie.genMac.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.cbmie.system.entity.User;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringEscapeUtils;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.AffiAssureInfoDao;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.baseinfo.entity.AffiAssureInfo;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__担保service
 */
@Service
@Transactional
public class AffiAssureInfoService extends BaseService<AffiAssureInfo, Long> {

	@Autowired
	private AffiAssureInfoDao affiAssureInfoDao;

	@Override
	public HibernateDao<AffiAssureInfo, Long> getEntityDao() {
		return affiAssureInfoDao;
	}
	
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public List<AffiAssureInfo> getByParentId(String parentId) {
		List<AffiAssureInfo> affiAssureInfo = affiAssureInfoDao.findBy("parentId", parentId);
		return affiAssureInfo;
	}
	
	/**
	 * 保存担保子表
	 * @param affiBaseInfo
	 * @param affiAssureJson
	 */
	public void save(AffiBaseInfo affiBaseInfo, String affiAssureJson) {
		// 转成标准的json字符串
		affiAssureJson = StringEscapeUtils.unescapeHtml4(affiAssureJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AffiAssureInfo> affiAssureInfoList = new ArrayList<AffiAssureInfo>();
		try {
			JsonNode jsonNode = objectMapper.readTree(affiAssureJson);
			for (JsonNode jn : jsonNode) {
				AffiAssureInfo affiBankInfo = objectMapper.readValue(jn.toString(), AffiAssureInfo.class);
				affiAssureInfoList.add(affiBankInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取goods中goodsChild持久化对象
		List<AffiAssureInfo> dataAffiAssureInfoList = affiBaseInfo.getAffiAssureInfo();
		// 将数据库数据放入映射
		Map<Long, AffiAssureInfo> dataAffiAssureMap = new HashMap<Long, AffiAssureInfo>(); 
		for (AffiAssureInfo dataAffiAssureInfo : dataAffiAssureInfoList) {
			dataAffiAssureMap.put(dataAffiAssureInfo.getId(), dataAffiAssureInfo);
		}
		// 排除没有发生改变的
		for (AffiAssureInfo dataAffiAssureInfo : dataAffiAssureInfoList) {
			if (affiAssureInfoList.contains(dataAffiAssureInfo)) {
				affiAssureInfoList.remove(dataAffiAssureInfo);
				dataAffiAssureMap.remove(dataAffiAssureInfo.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AffiAssureInfo affiAssureInfo : affiAssureInfoList) {
			if (affiAssureInfo.getId() == null) {
				insert(affiBaseInfo.getId(), affiAssureInfo, dataAffiAssureInfoList); // 新增 
			}
			AffiAssureInfo dataAffiAssureInfo = dataAffiAssureMap.get(affiAssureInfo.getId());
			if (dataAffiAssureInfo != null) {
				update(dataAffiAssureInfo, affiAssureInfo); // 修改
				dataAffiAssureMap.remove(affiAssureInfo.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AffiAssureInfo> entry : dataAffiAssureMap.entrySet()) {
			dataAffiAssureInfoList.remove(entry.getValue());
			affiAssureInfoDao.delete(entry.getKey());
		}
	}

	private void update(AffiAssureInfo dataAffiAssureInfo, AffiAssureInfo affiAssureInfo) {
		User currentUser = UserUtil.getCurrentUser();
		dataAffiAssureInfo.setGuarantee(affiAssureInfo.getGuarantee());
		dataAffiAssureInfo.setPledge(affiAssureInfo.getPledge());
		dataAffiAssureInfo.setChattel(affiAssureInfo.getChattel());
		dataAffiAssureInfo.setRealEstate(affiAssureInfo.getRealEstate());
		dataAffiAssureInfo.setUpdaterNo(currentUser.getLoginName());
		dataAffiAssureInfo.setUpdaterName(currentUser.getName());
		dataAffiAssureInfo.setUpdateDate(new Date());
	}
	
	private void insert(Long parentId, AffiAssureInfo affiAssureInfo, List<AffiAssureInfo> dataAffiAssureInfoList) {
		User currentUser = UserUtil.getCurrentUser();
		affiAssureInfo.setPid(parentId);
		affiAssureInfo.setCreaterNo(currentUser.getLoginName());
		affiAssureInfo.setCreaterName(currentUser.getName());
		affiAssureInfo.setCreateDate(new Date());
		dataAffiAssureInfoList.add(affiAssureInfo);
	}
	
}
