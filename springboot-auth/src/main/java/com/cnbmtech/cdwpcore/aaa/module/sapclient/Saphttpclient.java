/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.sapclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnbmtech.cdwpcore.aaa.utils.ConfigUtil;

public final class Saphttpclient{

    //public static final String URL = SpringUtil.getBean(SystemConfig.class).sapQueryUrl;
	public static final String URL = ConfigUtil.instance.getKey("system.sapQueryUrl");
	
    public static void main(String[] args) {

        JSONObject jsobj1 = new JSONObject();
        JSONObject jsobj2 = new JSONObject();
        jsobj2.put("ZPC_NUM", "111111113333333");
        JSONArray jsona = new JSONArray();
        jsona.add(jsobj2);
        jsobj1.put("IN_DATA", jsona);
        System.out.println(jsobj1.toJSONString());
        post(jsobj1);

    }

    public static String post(final JSONObject json) {
        final HttpClient client = HttpClients.createDefault();
        final HttpPost post = new HttpPost(URL);

        post.setHeader("Content-Type", "application/json");
        // post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            final StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            final HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            final InputStream inStream = httpResponse.getEntity().getContent();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            final StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            //System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                System.out.println("请求服务器成功，做相应处理");

            } else {

                System.out.println("请求服务端失败");

            }

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }
    
    public static String post(final JSONObject json, final String url) {
        final HttpClient client = HttpClients.createDefault();
        final HttpPost post = new HttpPost(url);

        post.setHeader("Content-Type", "application/json");
        // post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            final StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            final HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            final InputStream inStream = httpResponse.getEntity().getContent();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            final StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            //System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                System.out.println("请求服务器成功，做相应处理");

            } else {

                System.out.println("请求服务端失败");

            }

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    
	}
}
