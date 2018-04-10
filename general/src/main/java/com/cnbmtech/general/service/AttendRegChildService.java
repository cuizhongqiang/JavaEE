package com.cnbmtech.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.AttendRegChildDao;
import com.cnbmtech.general.entity.AttendReg;
import com.cnbmtech.general.entity.AttendRegChild;

@Service
public class AttendRegChildService extends BaseService<AttendRegChild, Long>{
	
	@Autowired
	private AttendRegChildDao attendRegChildDao;
	
	@Override
	public BaseDao<AttendRegChild, Long> getEntityDao() {
		return attendRegChildDao;
	}
	
	public void insert(AttendReg attendReg) {
		String[] remarkName = attendReg.getRemarkName();
		String[] remarkType = attendReg.getRemarkType();
		String[] remarkValue = attendReg.getRemarkValue();
		String[] remarkDictCode = attendReg.getRemarkDictCode();
		for (int i = 0; i < remarkName.length; i++) {
			AttendRegChild arc = new AttendRegChild();
			arc.setName(remarkName[i]);
			arc.setType(remarkType[i]);
			arc.setValue(remarkValue[i]);
			arc.setDictCode(remarkDictCode[i]);
			arc.setPid(attendReg.getId());
			super.save(arc);
		}
	}
	
	public void update(AttendReg attendReg) {
		String[] remarkName = attendReg.getRemarkName();
		String[] remarkType = attendReg.getRemarkType();
		String[] remarkValue = attendReg.getRemarkValue();
		for (int i = 0; i < remarkName.length; i++) {
			for (AttendRegChild arc : attendReg.getChildren()) {
				if (arc.getName().equals(remarkName[i]) && arc.getType().equals(remarkType[i])) {
					arc.setValue(i < remarkValue.length ? remarkValue[i] : "");
					super.save(arc);
				}
			}
		}
	}
	
}
