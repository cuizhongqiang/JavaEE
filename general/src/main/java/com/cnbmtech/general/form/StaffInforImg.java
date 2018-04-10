package com.cnbmtech.general.form;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.cnbmtech.general.entity.StaffInfor;

public class StaffInforImg extends StaffInfor {
	
	private static final long serialVersionUID = 7938733844504747183L;
	
	@Excel(name = "简介二维码", type = 2, imageType = 2, width = 20, height = 30)
	private byte[] img;

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
