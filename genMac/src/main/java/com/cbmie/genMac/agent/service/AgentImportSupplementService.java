package com.cbmie.genMac.agent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.agent.dao.AgentImportSupplementDao;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.agent.entity.AgentImportSupplement;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 代理进口补充service
 */
@Service
@Transactional
public class AgentImportSupplementService extends BaseService<AgentImportSupplement, Long> {

	@Autowired
	private AgentImportSupplementDao agentImportSupplementDao;
	
	@Autowired
	private AgentImportService agentImportService;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<AgentImportSupplement, Long> getEntityDao() {
		return agentImportSupplementDao;
	}
	
	public AgentImportSupplement findByNo(Long id, String no){
		return agentImportSupplementDao.findByNo(id, no);
	}
	
	public String generateCode(Long pid) {
		AgentImport agentImport = agentImportService.get(pid);
		List<AgentImportSupplement> supplementList = agentImport.getAgentImportSupplement();
		List<Integer> intList = new ArrayList<Integer>();
		for (AgentImportSupplement as : supplementList) {
			Pattern p = Pattern.compile("-B(\\d+)$");
			Matcher m = p.matcher(as.getAgreementNo());
			if (m.find()) {
				intList.add(Integer.valueOf(m.group(1)));
			}
		}
		String num = documentCode.getSupplement(intList);
		return agentImport.getContractNo() + "-B" + num;
	}
	
}
