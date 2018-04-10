package com.cbmie.genMac.credit.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.credit.entity.OpenCreditHistory;

/**
 * 开证历史DAO
 */
@Repository
public class OpenCreditHistoryDao extends HibernateDao<OpenCreditHistory, Long> {

}
