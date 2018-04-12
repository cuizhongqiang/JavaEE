
/**  
* @Title: ConfigUtil.java
* @Package com.cnbmtech.cdwpcore.sapclient.utils
* @Description: 配置文件工具类，单例模式。
* @author markzgwu
* @date 2017年12月7日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.utils;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public enum ConfigUtil {
	instance;
	private ConfigUtil(){
		final Configurations configs = new Configurations();
		// Read data from this file
		final String propertiesFile = "config/system.properties";
		//final File file = new File(ConfigUtil.class.getClassLoader().getResource(propertiesFile).getFile());
		try {
			config = configs.properties(propertiesFile);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	PropertiesConfiguration config;
	public String getKey(final String key) {
		return config.getString(key);
	}
}
