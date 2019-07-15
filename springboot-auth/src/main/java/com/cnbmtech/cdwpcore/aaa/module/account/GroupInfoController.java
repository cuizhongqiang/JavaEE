package com.cnbmtech.cdwpcore.aaa.module.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DictionaryController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wy
 *  @date 2018年2月8日
 *
 */
@RestController
@RequestMapping("/groupInfo")
public class GroupInfoController {
	@Autowired
	GroupInfoRepository groupInfoRepository;

	@RequestMapping(value = {"/list"},method=RequestMethod.GET)
	List<GroupInfo> listGroupInfosa(){
		return groupInfoRepository.listGroupInfos();
	}
}
