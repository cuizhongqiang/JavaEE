package com.cnbmtech.base.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出excel工具类
 */
public class JXLExcel {
	
	// excel标题
	private String[] columnNames;
	
	// 属性名称
	private String[] dbColumnNames;
	
	/**
	 * 导出excel
	 * @param response	响应对象
	 * @param list	内容
	 * @param excelName	excel名称
	 */
	public <T> void exportExcel(HttpServletResponse response, List<T> list, String excelName) {
		WritableWorkbook book = null;
		OutputStream os = null;
		try {
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
			os = response.getOutputStream();
			// sheet名称
			String sheetName = "sheet1";
			// 全局设置
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("UTF-8");
			// 创建工作薄
			book = Workbook.createWorkbook(os);
			// 创建第一个工作表
			WritableSheet sheet = book.createSheet(sheetName, 1);
			sheet.getSettings().setVerticalFreeze(1);// 冻结表头
			// 添加标题
			addColumNameToWsheet(sheet);
			// 添加内容
			writeContext(sheet, list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (book != null) {
				// 写入文件
				try {
					book.write();
					book.close();
					os.flush();
					os.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private <T> void writeContext(WritableSheet wsheet, List<T> list) {
		int rows = list.size();
		jxl.write.Label wlabel = null;
		int cols = this.dbColumnNames.length;
		Object value = null;
		try {
			for (int i = 0; i < rows; i++) {
				T t = (T) list.get(i);
				for (int j = 0; j < cols; j++) {
					value = PropertyUtils.getProperty(t, dbColumnNames[j]);
					wlabel = new jxl.write.Label(j, (i + 1), value + "");
					wsheet.addCell(wlabel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addColumNameToWsheet(jxl.write.WritableSheet wsheet) throws RowsExceededException, WriteException {
		// 设置excel标题样式
		jxl.write.WritableCellFormat wcfFC = getFormat();

		jxl.write.Label wlabel = null;
		String[] columNames = this.columnNames;
		int colSize = columNames.length;

		Integer[] colsWidth = new Integer[colSize];;
		for (int i = 0; i < colSize; i++) {
			colsWidth[i] = 20;
		}

		int temp = 0;
		String colName = null;
		for (int i = 0; i < colSize; i++) {
			colName = columNames[i];
			if (null == colName || "".equals(colName))
				colName = "";
			wlabel = new jxl.write.Label(i, 0, colName, wcfFC);
			wsheet.addCell(wlabel);
			temp = colsWidth[i].intValue();
			// 默认设置列宽
			temp = temp == 0 ? 20 : temp;
			wsheet.setColumnView(i, temp);
		}

	}

	// 设置格式
	private WritableCellFormat getFormat() {

		jxl.write.WritableFont wfont = getFont();
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfont);
		try {
			wcfFC.setWrap(true);// 自动换行
			wcfFC.setAlignment(Alignment.CENTRE);
			wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置对齐方式
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcfFC;
	}

	// 设置字体
	private WritableFont getFont() {
		return new WritableFont(WritableFont.COURIER, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String[] getDbColumnNames() {
		return dbColumnNames;
	}

	public void setDbColumnNames(String[] dbColumnNames) {
		this.dbColumnNames = dbColumnNames;
	}
}
