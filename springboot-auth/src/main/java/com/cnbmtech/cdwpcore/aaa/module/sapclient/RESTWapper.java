/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.sapclient;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnbmtech.cdwpcore.aaa.module.manager.Supplier;

public final class RESTWapper {

	public static String BAPI_VENDOR_DISPLAY() {
		JSONObject jsobj1 = new JSONObject();
		JSONObject jsobj2 = new JSONObject();
		jsobj2.put("ZPC_NUM", "111111113333333");
		JSONArray jsona = new JSONArray();
		jsona.add(jsobj2);
		jsobj1.put("IN_DATA", jsona);
		// System.out.println(jsobj1.toJSONString());
		String json = Saphttpclient.post(jsobj1);
		return json;
	}

	public static String BAPI_VENDOR_DISPLAY(final String query) {
		JSONObject jsobj1 = new JSONObject();
		JSONObject jsobj2 = new JSONObject();
		jsobj2.put("ZPC_NUM", "111111113333333");
		JSONArray jsona = new JSONArray();
		jsona.add(jsobj2);
		jsobj1.put("IN_DATA", jsona);
		// System.out.println(jsobj1.toJSONString());
		String json = Saphttpclient.post(jsobj1);
		return json;
	}

	public static String getSupplierList() {
		final String url = "http://it-saptest.chinacloudapp.cn:8000/sap/api/query/common?sap-client=500";
		final String sqlstr = "SELECT LIFNR AS LIFNR, LAND1 AS LAND1, NAME1 AS NAME1, NAME2 AS NAME2, SORTL AS SORTL, REGIO AS REGIO, ORT01 AS ORT01, PSTLZ AS PSTLZ, STRAS AS STRAS, TELF1 AS TELF1, TELFX AS TELFX, STCD1 AS STCD1, KTOKK AS KTOKK, ERDAT AS ERDAT, ERNAM AS ERNAM  FROM ZVMM_LFA1 WHERE MANDT='500'";
		final JSONObject jo = new JSONObject();
		jo.put("SQL", sqlstr);
		final JSONArray ja = new JSONArray();
		JSONObject fieldJo = new JSONObject();

		fieldJo.put("FIELDNAME", "LIFNR");
		fieldJo.put("FIELDTYPE", "LIFNR");
		ja.add(fieldJo);
		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "LAND1");
		fieldJo.put("FIELDTYPE", "LAND1_GP");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "NAME1");
		fieldJo.put("FIELDTYPE", "AD_NAME1");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "NAME2");
		fieldJo.put("FIELDTYPE", "AD_NAME2");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "SORTL");
		fieldJo.put("FIELDTYPE", "SORTL");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "REGIO");
		fieldJo.put("FIELDTYPE", "REGIO");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "ORT01");
		fieldJo.put("FIELDTYPE", "ORT01_GP");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "PSTLZ");
		fieldJo.put("FIELDTYPE", "PSTLZ");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "STRAS");
		fieldJo.put("FIELDTYPE", "STRAS_GP");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "TELF1");
		fieldJo.put("FIELDTYPE", "TELF1");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "TELFX");
		fieldJo.put("FIELDTYPE", "TELFX");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "STCD1");
		fieldJo.put("FIELDTYPE", "STCD1");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "KTOKK");
		fieldJo.put("FIELDTYPE", "KTOKK");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "ERDAT");
		fieldJo.put("FIELDTYPE", "ERDAT_RF");
		ja.add(fieldJo);

		fieldJo = new JSONObject();
		fieldJo.put("FIELDNAME", "ERNAM");
		fieldJo.put("FIELDTYPE", "ERNAM_RF");
		ja.add(fieldJo);

		jo.put("FIELDS", ja);
		String result = Saphttpclient.post(jo, url);
		return result;
	}

	public static void main(String[] args) {
		// BAPI_MONITOR_GETLIST();
		// BAPI_VENDOR_GETDETAIL();
		// BAPI_VENDOR_DISPLAY();
		String json = getSupplierList();
		//System.out.println(json);
		JSONObject jsonObj = JSONObject.parseObject(json);
		System.out.println(jsonObj.keySet());
		String json2 = jsonObj.getString("RESULT");
		//System.out.println(json2);
		//List<Supplier> supplisers = JsonUtils.jsonToBeans(json2, Supplier.class);
		List<Supplier> supplisers = JSONObject.parseArray(json2, Supplier.class);
		System.out.println(supplisers.size());
	}
}
