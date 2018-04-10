package com.cnbmtech.base.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.base.utils.PropertyFilter.MatchType;

public abstract class BaseService<T, PK extends Serializable> {

	/**
	 * 子类需要实现该方法，提供注入的dao
	 */
	public abstract BaseDao<T, PK> getEntityDao();
	
	@Transactional(readOnly = true)
	public T get(PK id) {
		return getEntityDao().findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<T> getAll(List<PropertyFilter> filters) {
		Specification<T> specification = buildSpecification(filters);
		List<T> list = getEntityDao().findAll(specification, new Sort(new Order(Direction.DESC, "id")));
		return list;
	}
	
	@Transactional(readOnly = true)
	public Page<T> findPage(HttpServletRequest request, List<PropertyFilter> filters) {
		
		int pageNumber = 1; // 当前页码
		int pageSize = 10; // 每页行数
		String orderBy = "id"; // 排序字段
		Direction order = Direction.DESC; // 排序顺序

		if (StringUtils.isNotEmpty(request.getParameter("pageNumber")))
			pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
		if (StringUtils.isNotEmpty(request.getParameter("pageSize")))
			pageSize = Integer.valueOf(request.getParameter("pageSize"));
		if (StringUtils.isNotEmpty(request.getParameter("sortName")))
			orderBy = request.getParameter("sortName").toString();
		if (StringUtils.isNotEmpty(request.getParameter("sortOrder")) && request.getParameter("sortOrder").equalsIgnoreCase("asc"))
			order = Direction.ASC;
		
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(new Order(order, orderBy)));
		Specification<T> specification = buildSpecification(filters);
		return getEntityDao().findAll(specification, pageable);
	}
	
	private Specification<T> buildSpecification(final List<PropertyFilter> filters) {
		Specification<T> specification = new Specification<T>() {
			/*
			 * @param root:代表的查询的实体类
			 * 
			 * @param query：可以从中得到Root对象，即告知JPA Criteria查询要查询哪一个实体类，
			 * 还可以来添加查询条件，还可以结合EntityManager对象得到最终查询的TypedQuery 对象
			 * 
			 * @param buildre对象，用于创建Criteria相关的对象工程，当然可以从中获取到predicate类型
			 * 
			 * @return:代表一个查询条件
			 */
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (PropertyFilter filter : filters) {
					Predicate predicate = null;
					if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
						predicate = buildPredicate(filter.getPropertyName(), filter.getMatchType(), filter.getMatchValue(), root, builder);
					} else {//包含多个属性需要比较的情况,进行or处理.
						List<Predicate> ors = new ArrayList<Predicate>();
						for (String param : filter.getPropertyNames()) {
							Predicate or = buildPredicate(param, filter.getMatchType(), filter.getMatchValue(), root, builder);
							ors.add(or);
						}
						predicate = builder.or(ors.toArray(new Predicate[ors.size()]));
					}
					predicates.add(predicate);
				}
				return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
		return specification;
	}
	
	private Predicate buildPredicate(String propertyName, MatchType matchType, Object propertyValue, Root<T> root, CriteriaBuilder builder) {
		Predicate predicate = null;
		Path<Object> field = root.get(propertyName);
		switch (matchType) {
			case EQ:
				predicate = builder.equal(field, propertyValue);
				break;
			case LIKE:
				predicate = builder.like(field.as(String.class), "%" + propertyValue + "%");
				break;
			case BETWEEN:
				@SuppressWarnings("unchecked")
				Map<String, Date> map = (Map<String, Date>) propertyValue;
				predicate = builder.between(field.as(Date.class), map.get("start"), map.get("end"));
				break;
			case GE:
				predicate = builder.ge(field.as(Number.class), (Number) propertyValue);
				break;
			case GT:
				predicate = builder.gt(field.as(Number.class), (Number) propertyValue);
				break;
			case LE:
				predicate = builder.le(field.as(Number.class), (Number) propertyValue);
				break;
			case LT:
				predicate = builder.lt(field.as(Number.class), (Number) propertyValue);
				break;
			case ISNOTNULL:
				predicate = builder.isNotNull(field);
				break;
			case ISNULL:
				predicate = builder.isNull(field);
				break;
			case NE:
				predicate = builder.notEqual(field, propertyValue);
		}
		return predicate;
	}
	
	@Transactional(readOnly = false)
	public void insert(T entity) {
		getEntityDao().save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(PK id) {
		getEntityDao().delete(id);
	}
	
	@Transactional(readOnly = false)
	public void delete(T entity) {
		getEntityDao().delete(entity);
	}
	
	@Transactional(readOnly = false)
	public T update(T entity) {
		return getEntityDao().save(entity);
	}
	
	@Transactional(readOnly = false)
	public T save(T entity) {
		return getEntityDao().save(entity);
	}
	
	@Transactional(readOnly = true)
	public Iterable<T> findAll() {
		return getEntityDao().findAll();
	}
	
	@Transactional(readOnly = true)
	public List<T> getAll() {
		List<T> list = (List<T>) getEntityDao().findAll();
		return list;
	}
	
}
