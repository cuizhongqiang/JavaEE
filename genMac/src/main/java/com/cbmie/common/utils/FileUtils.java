package com.cbmie.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 读取文件工具类
 */
public class FileUtils {

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 读取项目中的指定文件
	 */
	public static String read(String fileName) throws IOException {
		Resource resource = resourceLoader.getResource(fileName);
		InputStream is = resource.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\r\n");
		}
		int last = sb.lastIndexOf("\r\n");
		br.close();
		isr.close();
		is.close();
		return sb.toString().substring(0, last);
	}

	/**
	 * 写入文件
	 * @param filePath 指定的文本文件
	 * @param append true表示追加，false表示重头开始写
	 * @param text 是要写入的文本字符串
	 */
	public static void write(String filePath, boolean append, String text) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			if (parentFile.mkdir()) {
				file.createNewFile();
			}
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append));
		out.write(text);
		out.flush();
		out.close();
	}
	
}
