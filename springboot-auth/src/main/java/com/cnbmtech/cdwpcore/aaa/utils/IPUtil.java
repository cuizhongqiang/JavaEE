
    /**  
    * @Title: IPUtil.java
    * @Package com.cnbmtech.cdwpcore.sapclient.utils
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月11日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
    * @ClassName: IPUtil
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月11日
    *
    */

public final class IPUtil {

	/**
	    * @Title: main
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param args    参数
	    * @return void    返回类型
	    * @throws
	    */
	
	public static String getServerAddr(){
		String addr = "localhost";
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}//获得本机IP 
		return addr;
	}
	
	public static void main(String[] args) {
		System.out.println(getServerAddr());
	}

}
