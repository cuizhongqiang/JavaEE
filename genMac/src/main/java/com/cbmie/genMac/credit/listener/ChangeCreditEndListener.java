package com.cbmie.genMac.credit.listener;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.credit.entity.ChangeCredit;
import com.cbmie.genMac.credit.entity.ChangeCreditGoods;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.entity.OpenCreditGoods;
import com.cbmie.genMac.credit.entity.OpenCreditHistory;
import com.cbmie.genMac.credit.entity.OpenCreditHistoryGoods;
import com.cbmie.genMac.credit.service.ChangeCreditGoodsService;
import com.cbmie.genMac.credit.service.ChangeCreditService;
import com.cbmie.genMac.credit.service.OpenCreditGoodsService;
import com.cbmie.genMac.credit.service.OpenCreditHistoryGoodsService;
import com.cbmie.genMac.credit.service.OpenCreditHistoryService;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ChangeCreditEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	ChangeCreditService changeCreditService;
	
	@Autowired
	OpenCreditService openCreditService;
	
	@Autowired
	OpenCreditHistoryService openCreditHistoryService;
	
	@Autowired
	ChangeCreditGoodsService changeCreditGoodsService;
	
	@Autowired
	OpenCreditGoodsService openCreditGoodsService;
	
	@Autowired
	OpenCreditHistoryGoodsService openCreditHistoryGoodsService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		ChangeCredit changeCredit = changeCreditService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			changeCredit.setState("作废");
		} else if (null != deleteReason) {
			changeCredit.setState("草稿");
			changeCredit.setProcessInstanceId(null);
		} else {
			// 获取要改的开证
			OpenCredit openCredit = openCreditService.get(changeCredit.getChangeId());
			// 把要改还没改的开证信息存入历史表
			OpenCreditHistory openCreditHistory = new OpenCreditHistory();
			BeanUtils.copyProperties(openCreditHistory, openCredit);
			openCreditHistory.setId(null);
			openCreditHistory.setChangeId(openCredit.getId());
			openCreditHistoryService.save(openCreditHistory);
			for (OpenCreditGoods openCreditGoods : openCredit.getOpenCreditGoods()) {
				OpenCreditHistoryGoods openCreditHistoryGoods = new OpenCreditHistoryGoods();
				BeanUtils.copyProperties(openCreditHistoryGoods, openCreditGoods);
				openCreditHistoryGoods.setId(null);
				openCreditHistoryGoods.setPid(openCreditHistory.getId());
				openCreditHistoryGoodsService.save(openCreditHistoryGoods);
			}
			// 把改证信息置入开证保存
			openCredit.setLcType(changeCredit.getLcType());
			openCredit.setDays(changeCredit.getDays());
			openCredit.setReceivable(changeCredit.getReceivable());
			openCredit.setReceipts(changeCredit.getReceipts());
			openCredit.setPercent(changeCredit.getPercent());
			openCredit.setBank(changeCredit.getBank());
			openCredit.setTheMoney(changeCredit.getTheMoney());
			openCredit.setOpenDate(changeCredit.getOpenDate());
			openCredit.setLcNo(changeCredit.getLcNo());
			openCredit.setRemark(changeCredit.getRemark());
			ObjectMapper objectMapper = new ObjectMapper();
			List<OpenCreditGoods> openCreditGoodsList = new ArrayList<OpenCreditGoods>();
			for (ChangeCreditGoods changeCreditGoods : changeCredit.getChangeCreditGoods()) {
				OpenCreditGoods openCreditGoods = new OpenCreditGoods();
				BeanUtils.copyProperties(openCreditGoods, changeCreditGoods);
				openCreditGoods.setId(null);
				openCreditGoodsList.add(openCreditGoods);
			}
			openCreditGoodsService.save(openCredit, objectMapper.writeValueAsString(openCreditGoodsList));
			// 修改改证表的状态，并存入历史id
			changeCredit.setState("生效");
			changeCredit.setHistoryId(openCreditHistory.getId());
		}
		changeCreditService.save(changeCredit);
	}

}
