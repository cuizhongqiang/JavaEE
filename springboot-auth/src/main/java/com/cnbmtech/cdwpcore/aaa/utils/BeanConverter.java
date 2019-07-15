package com.cnbmtech.cdwpcore.aaa.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BeanConverter {

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Object convertMap(final Class<?> type,final Map<?, ?> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> convertBean(final Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		final Class<?> type = bean.getClass();
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		final BeanInfo beanInfo = Introspector.getBeanInfo(type);
		final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			final PropertyDescriptor descriptor = propertyDescriptors[i];
			final String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				final Method readMethod = descriptor.getReadMethod();
				final Class<?> propertyType = descriptor.getPropertyType();
				final String propertyTypeName = propertyType.getName();
				// System.out.println(propertyTypeName);
				if (propertyTypeName.contains("java.util.")) {
					continue;
				}

				final Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	public static Map<String, Object> map(final Object bean) {

		Map<String, Object> returnMap = null;
		// org.springframework.beans.BeanUtils
		// org.apache.commons.beanutils.BeanUtils
		if (bean == null) {
			return returnMap;
		}
		try {
			returnMap = convertBean(bean);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	public static Map<String, String> map2string(final Object bean) {

		Map<String, String> returnMap = null;
		// org.springframework.beans.BeanUtils
		// org.apache.commons.beanutils.BeanUtils
		if (bean == null) {
			return returnMap;
		}
		try {
			returnMap = BeanUtils.describe(bean);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	// final static Gson gson = new Gson();
	public static String bean2json(final Object bean) {
		final Map<String, String> mapbean = map2string(bean);
		final String json = JSON.toJSONString(mapbean);
		// final String json = gson.toJson(bean);
		return json;
	}

	public static String list2json(final List<?> list) {
		// return list2jsonByMap(list);
		return list2jsonByFastjson(list);
	}

	public static String list2jsonByMap(final List<?> list) {
		final List<Map<String, Object>> beans = new ArrayList<Map<String, Object>>();
		list.forEach(obj -> {
			final Map<String, Object> mapbean = map(obj);
			// final String json = JSON.toJSONString(mapbean);
			beans.add(mapbean);
		});
		// final String json = gson.toJson(bean);
		return json(beans);
	}

	public static String list2jsonByFastjson(final List<?> list) {
		final String json = json(list);
		return json;
	}

	public static String json(final Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.IgnoreErrorGetter, SerializerFeature.SkipTransientField, SerializerFeature.UseISO8601DateFormat);
	}

	final static PropertyFilter filter = new PropertyFilter() {
		public boolean apply(Object source, String name, Object value) {
			boolean b1 = value.getClass().getName().contains("java.util.");
			boolean b2 = value == null;
			if (b1 || b2) {
				return false;
			}
			return true;

		}
	};
}