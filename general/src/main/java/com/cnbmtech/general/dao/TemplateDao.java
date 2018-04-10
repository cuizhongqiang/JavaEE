package com.cnbmtech.general.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.general.entity.Template;

@Repository
public interface TemplateDao extends BaseDao<Template, Long> {
	
}
