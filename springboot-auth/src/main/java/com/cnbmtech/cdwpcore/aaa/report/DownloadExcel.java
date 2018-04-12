
/**  
* @Title: DownloadExcel.java
* @Package com.cnbmtech.cdwpcore.aaa.report
* @Description: TODO
* @author zhengangwu
* @date 2018年1月26日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.report;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnbmtech.cdwpcore.aaa.module.manager.DataDictionary;
import com.cnbmtech.cdwpcore.aaa.module.manager.DataDictionaryRepository;

/**
 * @ClassName: DownloadExcel
 * @Description: TODO
 * @author zhengangwu
 * @date 2018年1月26日
 *
 */

@Controller
public final class DownloadExcel {
	
	@Autowired
	DataDictionaryRepository dataDictionaryRepository;
	
	@RequestMapping("/download/excel")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 告诉浏览器用什么软件可以打开此文件
		response.setHeader("content-Type", "application/vnd.ms-excel");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("数据字典", "UTF-8") + ".xls");
		// 编码
		response.setCharacterEncoding("UTF-8");
		List<DataDictionary> list = dataDictionaryRepository.findAll();// 获得用户
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), DataDictionary.class, list);
		workbook.write(response.getOutputStream());
	}
}
