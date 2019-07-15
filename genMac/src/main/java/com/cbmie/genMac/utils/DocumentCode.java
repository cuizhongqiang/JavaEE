package com.cbmie.genMac.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.SimpleHibernateDao;
import com.cbmie.common.utils.Reflections;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchase;
import com.cbmie.genMac.selfRun.entity.SelfPurchase;
import com.cbmie.genMac.selfRun.entity.SelfSales;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 文档编码
 * @author czq
 * @see 年份+制作人+文档类型+文档签订日+客户识别号+顺序自编号
 */

@Service
@Transactional
public class DocumentCode {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	/**
	 * 年份
	 */
	public String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy", Locale.CHINESE);
		return sdf.format(new Date());
	}
	
	/**
	 * 制作人
	 */
	public String getProducer(String chineseName) {
		String convert = getPinyinHeadChar(chineseName);;
		return convert.substring(convert.length() - 2).toUpperCase();
	}
	
	/**
	 * 文档类型
	 */
	public String getType(String chineseName) {
		String convert = getPinyinHeadChar(chineseName);
		return convert.substring(0, 2).toUpperCase();
	}
	
	/**
	 * 文档签订日
	 */
	public String getSignedDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd", Locale.CHINESE);
		return sdf.format(new Date());
	}
	
	/**
	 * 客户识别号
	 */
	public String customerNum(String chineseName) {
		String convert = getPinyinHeadChar(chineseName);
		return convert.substring(0, 4).toUpperCase();
	}
	
	/**
	 * 顺序自编号
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> String getOrderNum(Class clazz, String fieldName, String value) {
		SimpleHibernateDao simpleHibernateDao = new SimpleHibernateDao(sessionFactory, clazz);
		Session session = null;
		List<T> dataList = new ArrayList<T>();
		if (clazz.getName().equals(AgentImport.class.getName())
				|| clazz.getName().equals(SelfPurchase.class.getName())
				|| clazz.getName().equals(SelfSales.class.getName())
				|| clazz.getName().equals(DomesticPurchase.class.getName())
				|| clazz.getName().equals(AgentPurchase.class.getName())) {
			session = sessionFactory.getCurrentSession();
			String sql = "SELECT contract_no FROM ("
					+ "SELECT contract_no FROM agent_import "
					+ "UNION "
					+ "SELECT contract_no FROM self_purchase "
					+ "UNION "
					+ "SELECT contract_no FROM self_sales "
					+ "UNION "
					+ "SELECT contract_no FROM domestic_purchase "
					+ "UNION "
					+ "SELECT contract_no FROM agent_purchase) a "
					+ "where a.contract_no LIKE '" + value + "%';";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			dataList = sqlQuery.list();
		} else {
			session = simpleHibernateDao.getSession();
			Criteria criteria = session.createCriteria(clazz);
			criteria.add(Restrictions.ilike(fieldName, value, MatchMode.START));
			dataList = criteria.list();
		}
		List<Integer> intList = new ArrayList<Integer>();
		for (T t : dataList) {
			String fieldValue = null;
			if (t instanceof String) {
				fieldValue = t.toString();
			} else {
				fieldValue = Reflections.invokeGetter(t, fieldName).toString();
			}
			intList.add(Integer.valueOf(fieldValue.substring(fieldValue.length() - 2)));
		}
		int orderNum = 1;
		for (int i : intList) {
			if (i >= orderNum) {
				orderNum = i + 1;
			}
		}
		String str = String.valueOf(orderNum);
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * 补充协议
	 */
	public String getSupplement(List<Integer> intList) {
		int orderNum = 1;
		for (int i : intList) {
			if (i >= orderNum) {
				orderNum = i + 1;
			}
		}
		return String.valueOf(orderNum);
	}
	
	/**
	 * 从合同号中取出客户识别号
	 */
	public String getCustomerNumFromContractNo(String contractNo) {
		return contractNo.substring(10, contractNo.length() - 2);
	}
	
	/**
	 * 获取汉字拼音首字母
	 */
	private String getPinyinHeadChar(String chineseName) {
		String convert = "";
		for (int i = 0; i < chineseName.length(); i++) {
			char c = chineseName.charAt(i);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			}
		}
		convert = convert.trim().replace(" ", "");
		return convert;
	}
	
	/** 组合方法 **/
	
	/**
	 * 年份+制作人+文档类型+文档签订日
	 */
	public String combination(String documentType) {
		User currentUser = UserUtil.getCurrentUser();
		String year = getYear();//年份
		String producer = getProducer(currentUser.getName());//制作人
		String type = getType(documentType);//文档类型
		String signedDay = getSignedDay();//文档签订日
		return year + producer + type + signedDay;
	}
	
}
