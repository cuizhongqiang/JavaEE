package com.cbmie.genMac.baseinfo.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.cbmie.genMac.baseinfo.dao.AffiBankInfoDao;
import com.cbmie.genMac.baseinfo.entity.AffiBankInfo;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__银行信息service
 */
@Service
@Transactional
public class AffiBankInfoService extends BaseService<AffiBankInfo, Long> {

	@Autowired
	private AffiBankInfoDao affiBankInfoDao;

	@Override
	public HibernateDao<AffiBankInfo, Long> getEntityDao() {
		return affiBankInfoDao;
	}
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public List<AffiBankInfo> getByParentId(String parentId) {
		List<AffiBankInfo> affiBankInfoList = affiBankInfoDao.findBy("parentId", parentId);
		return affiBankInfoList;
	}
	
	/**
	 * 根据bankNo获取对象
	 * @param banNO
	 * @return
	 */
	public AffiBankInfo getByNo(String banNO){
		AffiBankInfo affiBankInfo = affiBankInfoDao.findUniqueBy("bankNO", banNO);
		return affiBankInfo;
	}
	
	
	
	/**
	 * 
	 * @param affiBaseInfo
	 * @param affiBankJson
	 */
	public void save(AffiBaseInfo affiBaseInfo, String affiBankJson) {
		// 转成标准的json字符串
		affiBankJson = StringEscapeUtils.unescapeHtml4(affiBankJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AffiBankInfo> affiBankInfoList = new ArrayList<AffiBankInfo>();
		try {
			JsonNode jsonNode = objectMapper.readTree(affiBankJson);
			for (JsonNode jn : jsonNode) {
				AffiBankInfo affiBankInfo = objectMapper.readValue(jn.toString(), AffiBankInfo.class);
				affiBankInfoList.add(affiBankInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取goods中goodsChild持久化对象
		List<AffiBankInfo> dataAffiBankInfoList = affiBaseInfo.getAffiBankInfo();
		// 将数据库数据放入映射
		Map<Long, AffiBankInfo> dataAffiBankMap = new HashMap<Long, AffiBankInfo>(); 
		for (AffiBankInfo dataAffiBankInfo : dataAffiBankInfoList) {
			dataAffiBankMap.put(dataAffiBankInfo.getId(), dataAffiBankInfo);
		}
		// 排除没有发生改变的
		for (AffiBankInfo dataAffiBankInfo : dataAffiBankInfoList) {
			if (affiBankInfoList.contains(dataAffiBankInfo)) {
				affiBankInfoList.remove(dataAffiBankInfo);
				dataAffiBankMap.remove(dataAffiBankInfo.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AffiBankInfo affiBankInfo : affiBankInfoList) {
			if (affiBankInfo.getId() == null) {
				insert(affiBaseInfo.getId(), affiBankInfo, dataAffiBankInfoList); // 新增 
			}
			AffiBankInfo dataAffiBankInfo = dataAffiBankMap.get(affiBankInfo.getId());
			if (dataAffiBankInfo != null) {
				update(dataAffiBankInfo, affiBankInfo); // 修改
				dataAffiBankMap.remove(affiBankInfo.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AffiBankInfo> entry : dataAffiBankMap.entrySet()) {
			dataAffiBankInfoList.remove(entry.getValue());
			affiBankInfoDao.delete(entry.getKey());
		}
	}

	private void update(AffiBankInfo dataAffiBankInfo, AffiBankInfo affiBankInfo) {
		User currentUser = UserUtil.getCurrentUser();
		dataAffiBankInfo.setBankName(affiBankInfo.getBankName());
		dataAffiBankInfo.setBankNO(affiBankInfo.getBankNO());
		dataAffiBankInfo.setPartitionInfo(affiBankInfo.getPartitionInfo());
		dataAffiBankInfo.setContactPerson(affiBankInfo.getContactPerson());
		dataAffiBankInfo.setPhoneContact(affiBankInfo.getPhoneContact());
		dataAffiBankInfo.setUpdaterNo(currentUser.getLoginName());
		dataAffiBankInfo.setUpdaterName(currentUser.getName());
		dataAffiBankInfo.setUpdateDate(new Date());
	}
	
	private void insert(Long parentId, AffiBankInfo affiBankInfo, List<AffiBankInfo> dataAffiBankInfoList) {
		User currentUser = UserUtil.getCurrentUser();
		affiBankInfo.setParentId(Long.toString(parentId));
		affiBankInfo.setCreaterNo(currentUser.getLoginName());
		affiBankInfo.setCreaterName(currentUser.getName());
		affiBankInfo.setCreateDate(new Date());
		dataAffiBankInfoList.add(affiBankInfo);
	}
	
}
