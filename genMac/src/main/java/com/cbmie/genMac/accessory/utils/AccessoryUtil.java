package com.cbmie.genMac.accessory.utils;

import java.io.File;
import java.io.IOException;

import com.cbmie.genMac.accessory.entity.Accessory;

/**
 * 附件工具类
 */
public class AccessoryUtil {
	/**
	 * 从服务器删除文件
	 * 
	 * @param accessory
	 * @return flag
	 * @throws IOException
	 */
	public static boolean deleteFile(Accessory accessory) throws IOException {
		String systemSeparator = System.getProperty("file.separator");
		boolean flag = false;
		String path = accessory.getAccSrc() + systemSeparator + accessory.getAccId();
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

}