package com.cnbmtech.general.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.properties.ServerProperty;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.base.utils.QrCodeCreateUtil;
import com.cnbmtech.general.dao.StaffInforDao;
import com.cnbmtech.general.entity.StaffInfor;
import com.cnbmtech.general.form.StaffInforImg;

@Service
public class StaffInforService extends BaseService<StaffInfor, Long> {

	@Autowired
	private StaffInforDao staffInforDao;

	@Autowired
	private ServerProperty serverProperty;

	@Override
	public BaseDao<StaffInfor, Long> getEntityDao() {
		return staffInforDao;
	}

	public void importExcel(MultipartFile multipartFile) throws Exception {
		ImportParams params = new ImportParams();
		params.setNeedVerfiy(true);
		InputStream is = multipartFile.getInputStream();
		List<StaffInfor> list = ExcelImportUtil.importExcel(is, StaffInfor.class, params);
		for (StaffInfor staffInfor : list) {
			if (StringUtils.isNotBlank(staffInfor.getSysCode())) {
				StaffInfor data = staffInforDao.findBySysCode(staffInfor.getSysCode());
				if (data != null) {
					staffInfor.setId(data.getId());
					staffInfor.setCreateDate(data.getCreateDate());
					staffInfor.setUpdateDate(new Date());
				} else {
					staffInfor.setCreateDate(new Date());
					staffInfor.setRelationBrief(1l);
				}
			} else {
				staffInfor.setCreateDate(new Date());
				staffInfor.setRelationBrief(1l);
			}
			staffInforDao.save(staffInfor);
		}
	}

	public void writeExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("模版.xls", "UTF-8"));
		OutputStream os = response.getOutputStream();
		List<StaffInfor> list = getAll();
		List<StaffInforImg> excelList = new ArrayList<StaffInforImg>();
		if (list.size() > 0) {
			for (StaffInfor param : list) {
				StaffInforImg staffInforImg = new StaffInforImg();
				BeanUtils.copyProperties(staffInforImg, param);
				String url = "http://" + serverProperty.getUrl() + "/vistor/toVisitor/" + param.getId();
				staffInforImg.setImg(QrCodeCreateUtil.getImg4Excel(url, 150, 150, "png"));
				excelList.add(staffInforImg);
			}
		}
		ExportParams params = new ExportParams();
		params.setSheetName("员工信息");
		Workbook workbook = ExcelExportUtil.exportExcel(params, StaffInforImg.class, excelList);
		workbook.write(os);
		os.close();

	}

}
