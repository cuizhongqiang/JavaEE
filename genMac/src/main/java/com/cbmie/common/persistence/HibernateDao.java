package com.cbmie.common.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.cbmie.common.persistence.PropertyFilter.MatchType;
import com.cbmie.common.utils.Reflections;
import com.cbmie.common.utils.StringUtils;


/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
public class HibernateDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {
		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}
	
	/**
	 * 简单sql
	 * @param page
	 * @param filterMap
	 * @return
	 */
	public Page<T> sqlSearch(final Page<T> page, String sql, final Map<String, Object> filterMap) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_count = new StringBuffer(sql);
		sql_count.replace(sql_count.indexOf("select") + 7, sql_count.indexOf("from") - 1, "count(*)");
		SQLQuery query_count = session.createSQLQuery(sql_count.toString());
		if(filterMap != null) {
			for(String mapKey : filterMap.keySet()) {
				query_count.setParameter(mapKey, filterMap.get(mapKey));
			}
		}
		int count = Integer.parseInt(query_count.uniqueResult().toString());
		page.setTotalCount(count);
		
		SQLQuery query = session.createSQLQuery(sql);
		query.setFirstResult(page.getFirst() - 1);
		query.setMaxResults(page.getPageSize());
		if(filterMap != null) {
			for(String mapKey : filterMap.keySet()) {
				query.setParameter(mapKey, filterMap.get(mapKey));
			}
		}
		List list = query.list();
		page.setResult(list);
		return page;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) Reflections.getFieldValue(impl, "orderEntries");
			Reflections.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e);
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			Reflections.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
			logger.error(e.getMessage(),e);
		}

		return totalCount;
	}

	//-- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		//根据MatchType构造criterion
		switch (matchType) {
			case EQ:
				criterion = Restrictions.eq(propertyName, propertyValue);
				break;
			case NE:
				criterion = Restrictions.ne(propertyName, propertyValue);
				break;
			case LIKE:
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
				break;
			case BETWEEN:
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) propertyValue;
				criterion = Restrictions.between(propertyName, map.get("start"), map.get("end"));
				break;
			case LE:
				criterion = Restrictions.le(propertyName, propertyValue);
				break;
			case LT:
				criterion = Restrictions.lt(propertyName, propertyValue);
				break;
			case GE:
				criterion = Restrictions.ge(propertyName, propertyValue);
				break;
			case GT:
				criterion = Restrictions.gt(propertyName, propertyValue);
				break;
			case ISNULL:
				criterion = Restrictions.isNull(propertyName);
				break;
			case ISNOTNULL:
				criterion = Restrictions.isNotNull(propertyName);
		}
		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter
						.getMatchType());
				criterionList.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
	
	
	/*全文检索
	*//**
	 * 获取全文Session
	 *//*
	public FullTextSession getFullTextSession(){
		return Search.getFullTextSession(getSession());
	}
	
	*//**
	 * 建立索引
	 *//*
	public void createIndex(){
		try {
			getFullTextSession().createIndexer(entityClass).startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 全文检索
	 * @param page 分页对象
	 * @param query 关键字查询对象
	 * @param queryFilter 查询过滤对象
	 * @param sort 排序对象
	 * @return 分页对象
	 *//*
	@SuppressWarnings("unchecked")
	public Page<T> search(Page<T> page, BooleanQuery query, BooleanQuery queryFilter, Sort sort){
		
		// 按关键字查询
		FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, entityClass);
        
		// 过滤无效的内容
		if (queryFilter!=null){
			fullTextQuery.setFilter(new CachingWrapperFilter(new QueryWrapperFilter(queryFilter)));
		}
        
        // 设置排序
		if (sort!=null){
			fullTextQuery.setSort(sort);
		}

		// 定义分页
		page.setTotalCount(fullTextQuery.getResultSize());
		fullTextQuery.setFirstResult(page.getFirst() - 1);
		fullTextQuery.setMaxResults(page.getPageSize()); 

		// 先从持久化上下文中查找对象，如果没有再从二级缓存中查找
        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.QUERY); 
        
		// 返回结果
		page.setResult(fullTextQuery.list());
        
		return page;
	}
	
	*//**
	 * 获取全文查询对象
	 *//*
	public BooleanQuery getFullTextQuery(BooleanClause... booleanClauses){
		BooleanQuery booleanQuery = new BooleanQuery();
		for (BooleanClause booleanClause : booleanClauses){
			booleanQuery.add(booleanClause);
		}
		return booleanQuery;
	}

	*//**
	 * 获取全文查询对象
	 * @param q 查询关键字
	 * @param fields 查询字段
	 * @return 全文查询对象
	 *//*
	public BooleanQuery getFullTextQuery(String q, String... fields){
		Analyzer analyzer = new IKAnalyzer();
		BooleanQuery query = new BooleanQuery();
		try {
			if (StringUtils.isNotBlank(q)){
				for (String field : fields){
					QueryParser parser = new QueryParser(Version.LUCENE_36, field, analyzer);   
					query.add(parser.parse(q), Occur.SHOULD);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	*//**
	 * 设置关键字高亮
	 * @param query 查询对象
	 * @param list 设置高亮的内容列表
	 * @param subLength 截取长度
	 * @param fields 字段名
	 * @return 结果集合
	 *//*
	public List<T> keywordsHighlight(BooleanQuery query, List<T> list, int subLength, String... fields){
		Analyzer analyzer = new IKAnalyzer();
		Formatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");   
		Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query)); 
		highlighter.setTextFragmenter(new SimpleFragmenter(subLength)); 
		for(T entity : list){ 
			try {
				for (String field : fields){
					String text = StringUtils.replaceHtml((String)Reflections.invokeGetter(entity, field));
					String description = highlighter.getBestFragment(analyzer,field, text);
					if(description!=null){
						Reflections.invokeSetter(entity, fields[0], description);
						break;
					}
					Reflections.invokeSetter(entity, fields[0], StringUtils.abbr(text, subLength*2));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidTokenOffsetsException e) {
				e.printStackTrace();
			} 
		}
		return list;
	}*/
}
