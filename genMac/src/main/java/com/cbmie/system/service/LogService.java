package com.cbmie.system.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.LogDao;
import com.cbmie.system.entity.Log;
import com.cbmie.system.utils.JXLExcel;

/**
 * 日志service
 */
@Service
@Transactional(readOnly = true)
public class LogService extends BaseService<Log, Integer> {

	@Autowired
	private LogDao logDao;

	@Override
	public HibernateDao<Log, Integer> getEntityDao() {
		return logDao;
	}

	/**
	 * 批量删除日志
	 * 
	 * @param idList
	 */
	@Transactional(readOnly = false)
	public void deleteLog(List<Integer> idList) {
		logDao.deleteBatch(idList);
	}

	/**
	 * 导出excel
	 */
	@Transactional(readOnly = false)
	public void excelLog(HttpServletResponse response, List<Log> list) {
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "log.xls";
		// 设置标题
		String[] columnNames = new String[] { "操作编码", "操作用户名称", "日志类型", "系统类型", "浏览器类型", "IP地址", "物理地址", "执行时间", "详细描述",
				"请求参数", "日志生成时间" };
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "operationCode", "creater", "type", "os", "browser", "ip", "mac",
				"executeTime", "description", "requestParam", "createDate" };
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}

}
