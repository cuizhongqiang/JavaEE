package com.cnbmtech.cdwpcore.aaa.module.account.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName:
 * @Description: shiro配置
 * @author czq
 * @date 2018年2月7日
 */
@Entity
public class ShiroFilterConfig {
	@Id
	String ConfigName;
	String ConfigContent;
	public String getConfigName() {
		return ConfigName;
	}
	public void setConfigName(String configName) {
		ConfigName = configName;
	}
	public String getConfigContent() {
		return ConfigContent;
	}
	public void setConfigContent(String configContent) {
		ConfigContent = configContent;
	}

}
