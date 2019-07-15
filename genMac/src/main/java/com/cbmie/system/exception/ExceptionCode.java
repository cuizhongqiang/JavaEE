package com.cbmie.system.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionCode {
	/**默认异常编码*/
	public static final String DEFAULT_EXCEPTION_CODE = "0";
	/**权限异常编码*/
	public static final String AUTHORITY_EXCEPTION_CODE = "5";
	/**数据异常编码(数据库查询数据时的异常)*/
	public static final String DATA_EXCEPTION_CODE = "10";
	/**逻辑异常编码(后台处理时的异常)*/
	public static final String LOGIC_EXCEPTION_CODE = "15";
	/**逻辑异常编码(处理超时的异常)*/
	public static final String TIMEOUT_EXCEPTION_CODE = "20";
	
	public static final Map<String,Integer> EXCEPTION_STATUS_CODE_MAP = new HashMap<String,Integer>(){
		{
			put(DEFAULT_EXCEPTION_CODE,500);
			put(AUTHORITY_EXCEPTION_CODE,403);
			put(DATA_EXCEPTION_CODE,400);
			put(LOGIC_EXCEPTION_CODE,400);
			put(TIMEOUT_EXCEPTION_CODE,404);
		}
	};
}
