package com.cn.springbootjpa.base.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.common.PageRequest;
import com.cn.springbootjpa.base.common.PageResult;
import com.cn.springbootjpa.base.entity.BaseEntity;
import com.cn.springbootjpa.base.util.DaoUtil;
import com.cn.springbootjpa.base.util.UpdateTimeUtil;
import com.cn.springbootjpa.util.StringUtil;

/**
 * 数据库访问的基础DAO工具类
 * 
 * @author zhangyang
 *
 * @param <T>
 */
@Repository("baseDao")
public class BaseDaoImpl<T extends BaseEntity> extends HibernateDaoSupport implements IDao<T> {

	private final static int COUNT_WARN = 500;

	/**
	 * 实体对象类
	 */
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		if (ParameterizedType.class.isAssignableFrom(getClass().getGenericSuperclass().getClass())) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
	}

	/**
	 * 设置Hibernate Session工厂
	 * 
	 * @param sessionFactory
	 *            Hibernate Session工厂
	 */
	@Autowired
	public void setHibernateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private void addTypeMapping(NativeQuery<?> sqlQuery, Map<String, Type> typeMapping) {
		if (typeMapping != null) {
			Set<String> set = typeMapping.keySet();
			for (String key : set) {
				sqlQuery.addScalar(key, typeMapping.get(key));
			}
		}
	}

	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int bulkUpdate(String hql) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);
		Integer updateCount = super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(dhql);
			query.setProperties(paramss);
			return Integer.valueOf(query.executeUpdate());

		});
		return updateCount.intValue();
	}


	@Override
	public int bulkUpdate(final String hql, final Map<String, ?> params) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		if (params != null) {
			paramss.putAll(params);
		}
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);

		Integer updateCount = super.getHibernateTemplate().execute(session -> {
			Query<?> query = session.createQuery(dhql);
			query.setProperties(paramss);
			return Integer.valueOf(query.executeUpdate());

		});
		return updateCount.intValue();
	}

	@Override
	public int bulkUpdate4Send(final String hql, final Map<String, ?> params) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		if (params != null) {
			paramss.putAll(params);
		}
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);

		Integer updateCount = super.getHibernateTemplate().execute(session -> {
			Query<?> query = session.createQuery(dhql);
			query.setProperties(paramss);
			return Integer.valueOf(query.executeUpdate());

		});
		return updateCount.intValue();
	}


	@Override
	public void clear() {
		super.getHibernateTemplate().clear();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public void closeIterator(Iterator iter) {
		if (iter != null) {
			getHibernateTemplate().closeIterator(iter);
		}
	}

	@Override
	public long countByCriteria(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.getExecutableCriteria(currentSession()).uniqueResult();
	}


	public Filter enableFilter(String name) {
		return getHibernateTemplate().enableFilter(name);
	}


	@Override
	public void evict(Object entity) {
		this.getHibernateTemplate().evict(entity);
	}


	@Override
	public void executeProcedureSql(String queryString, Object[] params) {
		Session session = currentSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws HibernateException, SQLException {
				CallableStatement call = connection.prepareCall("{" + queryString + "}");
				if (params != null && params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						call.setObject(i + 1, params[i]);
					}
				}
				call.execute();
				call.close();
			}
		});
	}


	@Override
	public List<T> find(final String hql, final Map<String, ?> params) {
		return find(hql, params, 0, 0);
	}


	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<T> find(final String hql, final Map<String, ?> params, final int offsetIndex, final int pageSize) {
		return getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(hql);
			query.setProperties(params);
			if (pageSize > 0) {
				query.setFirstResult(offsetIndex);
				query.setMaxResults(pageSize);
			}

			// 增加查询数量结果日志
			List<T> list = query.list();
			loggingCount(list, hql, params);
			return list;

		});
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public List<T> find(String hql, Object... paramValues) {
		return (List<T>) super.getHibernateTemplate().find(hql, paramValues);
	}


	@Override
	public List<T> find(T example) {
		/* return super.getHibernateTemplate().findByExample(example); */

		List<T> list = super.getHibernateTemplate().findByExample(example);
		loggingCount(list, " from " + entityClass.getName());
		return list;
	}


	@Override
	public List<T> findAll() {
		/* return super.getHibernateTemplate().loadAll(this.entityClass); */

		List<T> list = super.getHibernateTemplate().loadAll(this.entityClass);
		loggingCount(list, " from " + entityClass.getName());
		return list;
	}


	@Override
	public List findByCriteria(DetachedCriteria criteria) {
		/* return super.getHibernateTemplate().findByCriteria(criteria); */
		List<?> list = super.getHibernateTemplate().findByCriteria(criteria);
		loggingCount(list, " from " + criteria.getExecutableCriteria(null).toString());
		return list;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		List list = super.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		loggingCount(list, " from " + criteria.getExecutableCriteria(null).toString() + " [" + firstResult + ","
				+ maxResults + "]");
		return list;
	}


	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	@Override
	public T findByField(String fieldName, String value) {
		Criteria criteria = currentSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(fieldName, value));
		List list = criteria.list();
		return list != null && list.size() > 0 ? (T) list.get(0) : null;
	}

	@Override
	public List<T> findByHql(final String hql, final String[] whereBodies, final Map<String, ?> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(hql);
		sb.append(generateHqlWhereClause(whereBodies, params));
		String finalHql = sb.toString();
		return find(finalHql, params, 0, 0);
	}


	@Override
	public List<T> findByHql(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
			final int offsetIndex, final int pageSize, final Order[] orders) {

		StringBuffer sb = new StringBuffer();
		sb.append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		sb.append(generateHqlOrderClause(orders));
		String finalHql = sb.toString();

		return find(finalHql, params, offsetIndex, pageSize);
	}


	@Override
	@SuppressWarnings("unchecked")
	public T findById(Long id) {
		if (id == null) {
			return null;
		}
		Object entity = super.getHibernateTemplate().load(this.entityClass, id);
		// fire actually query.
		return (T) (entity);
	}

	@Override
	public List<T> findByIds(Long... ids) {
		// TODO: implements it.
		return null;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List findByNamedQuery(String queryName) {
		Session session = super.currentSession();
		/* return super.getHibernateTemplate().findByNamedQuery(queryName); */
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		List list = query.list();
		loggingCount(list, queryName);
		return list;
	}


	@SuppressWarnings({ "rawtypes" })
	@Override
	public List findByNamedQuery(String queryName, Map<String, Object> params) {
		Session session = super.currentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		query.setProperties(params);
		List list = query.list();
		loggingCount(list, queryName, params);
		return list;
	}


	@SuppressWarnings({ "rawtypes" })
	@Override
	public List findByNamedQuery(String queryName, String paramName, Object value) {
		Session session = super.currentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		query.setParameter(paramName, value);
		List list = query.list();
		loggingCount(list, queryName + "[" + StringUtil.avoidNull(paramName) + "]");
		return list;
	}


	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public Map<Integer, T> findSpecFieldsValueByIds(Collection<String> fieldsNames, Collection<Long> ids,
			Map<String, Object> params) {
		Map<Integer, T> result = new HashMap<Integer, T>();
		if ((fieldsNames == null) || (fieldsNames.size() == 0) || (ids == null) || (ids.size() == 0)) {
			return result;
		}
		Map<String, Object> paramValid = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		// SELECT
		String entClassName = entityClass.getName();
		hql.append(" select new ").append(entClassName).append("( ent.id ");
		for (String fieldName : fieldsNames) {
			hql.append(",ent.").append(fieldName);
		}
		hql.append("  )");
		// FROM
		hql.append(" from ").append(entClassName).append(" as ent ");
		// WHERE
		hql.append(" where ent.id in (:IDS) ");
		if (params != null) {
			for (Map.Entry entry : params.entrySet()) {
				if (StringUtil.isNullOrBlank((String) entry.getKey()) == false) {
					String key = "Key_" + (String) entry.getKey();
					hql.append(" and ent.").append((String) entry.getKey()).append("= :").append(key);
					paramValid.put(key, entry.getValue());
				}
			}
		}
		Query query = currentSession().createQuery(hql.toString());
		query.setParameterList("IDS", ids);
		for (Map.Entry entry : paramValid.entrySet()) {
			query.setParameter((String) entry.getKey(), entry.getValue());
		}
		// FIND
		Collection<T> entities = query.list();
		if ((entities == null) || (entities.size() == 0)) {
			return result;
		}
		for (T ent : entities) {
			result.put(ent.getId(), ent);
		}
		return result;
	}

	/**
	 * Flush all pending saves, updates and deletes to the database.
	 */

	@Override
	public void flush() {
		super.getHibernateTemplate().flush();
	}


	private String generateHqlOrderClause(Order[] orders) {

		if (null == orders)
			return "";

		boolean isFirst = true;
		StringBuffer stringBuffer = new StringBuffer();
		for (Order order : orders) {
			if (order != null) {
				if (isFirst) {
					stringBuffer.append(" ORDER BY ");
					isFirst = false;
				} else {
					stringBuffer.append(", ");
				}
				stringBuffer.append(order.toString());
			}

		}
		return stringBuffer.toString();
	}


	private String generateHqlWhereClause(final String[] whereBodies, final Map<String, ?> params) {
		StringBuffer sb = new StringBuffer("");
		if (whereBodies != null) {
			boolean isHaveWhere = false;
			for (String whereBody : whereBodies) {
				String paramName = getWhereBodyParamName(whereBody);
				if (paramName == null || (params != null && params.get(paramName) != null)) {
					if (!isHaveWhere) {
						isHaveWhere = true;
						sb.append(" where ");
					} else {
						sb.append(" and ");
					}
					sb.append("(").append(whereBody).append(")");

				}
			}
		}
		return sb.toString();
	}


	@Override
	@SuppressWarnings("unchecked")
	public T getById(Long id) {
		Object obj = super.getHibernateTemplate().get(this.entityClass, id);
		if (null == obj)
			return null;
		return (T) (obj);
	}

	@Override
	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	/**
	 * function: 获取指定Sequence的新值。
	 * 
	 * @param sequenceName
	 * @return
	 */
	@Override
	public long getSequence(String sequenceName) {
		long newSeq = 0;
		String sql = "select " + sequenceName + ".Nextval from dual";
		try {
			NativeQuery<?> query = currentSession().createNativeQuery(sql);
			Object seq = query.uniqueResult();
			if (seq != null) {
				newSeq = Long.valueOf(seq.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return newSeq;
	}



	/**
	 * get the param name from a where body
	 * 
	 * @param ori
	 *            like "id = :idinput"
	 * @return "idinput"
	 */
	private String getWhereBodyParamName(String ori) {
		if (!ori.contains(":")) {
			return null;
		}

		String[] oris = ori.split("[:()]");
		if (oris.length == 1) {
			return null;
		} else {
			return oris[oris.length - 1].trim();
		}
	}

	/**
	 * Execute a HQL, then return the Iterator of the instance.
	 * 
	 * @param hql
	 *            The HQL will be executed.
	 * @param params
	 *            The parameters of the HQL.
	 * @return The instance of Iterator.
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Iterator iterate(final String hql, final Map<String, ?> params) {
		return getHibernateTemplate().execute(session -> {
			Query queryObject = session.createQuery(hql);
			queryObject.setProperties(params);
			return queryObject.iterate();

		});
	}

	// 记录数据查询返回数
	@SuppressWarnings("rawtypes")
	private void loggingCount(List list, String sql) {
		loggingCount(list, sql, null);
	}

	@SuppressWarnings("rawtypes")
	private void loggingCount(List list, String sql, Map<String, ?> params) {
		try {
			if (list == null) {
				list = new ArrayList();
			}
			int returnCount = list.size();
			if (list != null && returnCount > COUNT_WARN) {
				StringBuffer append = new StringBuffer().append("[Count]\t, ").append(returnCount).append("\t ")
						.append("\t[");
				append.append(sql);
				if (params != null && !params.isEmpty()) {
					append.append(",").append(params);
				}
				append.append("]");
				if (logger.isInfoEnabled()) {
					logger.info(append.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void marge(T o) {
		super.getHibernateTemplate().merge(o);
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param distinctName
	 *            distinct field
	 * @return return the list of result.
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int nativeQueryCountSQL(final String sql, final Map<String, ?> params, final String distinctName) {
		Object count = super.getHibernateTemplate().execute(session -> {
			String newSql = sql.toLowerCase();
			String countField = null;
			if (distinctName != null) {
				countField = "distinct " + distinctName;
			} else {
				countField = "*";
			}
			newSql = "select count(" + countField + ") from (" + sql + ")";
			NativeQuery<?> query = session.createNativeQuery(newSql);
			query.setProperties(params);
			List list = query.list();
			if (list != null && !list.isEmpty())
				return list.get(0);
			return null;

		});
		if (count != null) {
			return ((BigDecimal) count).intValue();
		}
		return 0;
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param distinctName
	 *            distinct field
	 * @return return the list of result.
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object nativeQueryCountSQL(final String sql, final String distinctName) {
		return super.getHibernateTemplate().execute(session -> {
			String newSql = sql.toLowerCase();
			int beginPos = newSql.indexOf(" from ");
			if (beginPos == -1) {
				throw new IllegalArgumentException("not a valid sql string");
			}
			String countField = null;
			if (distinctName != null) {
				countField = "distinct " + distinctName;
			} else {
				countField = "*";
			}
			newSql = "select count(" + countField + ")" + sql.substring(beginPos);
			NativeQuery<?> sqlQuery = session.createNativeQuery(newSql);
			List list = sqlQuery.list();
			if (list != null && !list.isEmpty())
				return ((BigDecimal) list.get(0)).intValue();
			return 0;

		});
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param the
	 *            query sql
	 * @return the list of result
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<?> nativeQuerySQL(final String sql) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> sqlQuery = session.createNativeQuery(sql);
			List list = sqlQuery.list();
			loggingCount(list, sql);
			return list;

		});
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	@SuppressWarnings({})
	@Override
	public List<?> nativeQuerySQL(final String sql, final int offsetIndex, final int pageSize) {
		return super.getHibernateTemplate().execute(session -> {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append(" Select main.* ");
			queryBuffer.append(" From  ");
			queryBuffer.append(" (Select t.*,rownum rn  ");
			queryBuffer.append("  from ( ");
			queryBuffer.append(sql);
			queryBuffer.append("       ) t  ");
			queryBuffer.append(" ) main ");
			queryBuffer.append(" where main.rn > ");
			queryBuffer.append(offsetIndex);
			queryBuffer.append(" and main.rn <=");
			queryBuffer.append(offsetIndex + pageSize);
			NativeQuery<?> sqlQuery = session.createNativeQuery(queryBuffer.toString());
			List<?> list = sqlQuery.list();
			loggingCount(list, sql);
			return list;

		});
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param param
	 *            the param of query SQL
	 * @return return the result of query
	 */
	@Override
	public List<?> nativeQuerySQL(final String sql, final Map<String, ?> params) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> query = session.createNativeQuery(sql);
			query.setProperties(params);
			List<?> list = query.list();
			loggingCount(list, sql, params);
			return list;
		});
	}

	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<?> nativeQuerySQL(final String sql, final Map<String, ?> params, final Class clazz) {
		return nativeQuerySQL(sql, params, null, clazz);
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            parameters of the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List nativeQuerySQL(final String sql, final Map<String, ?> params, final int offsetIndex,
			final int pageSize) {
		return nativeQuerySQL(sql, params, null, offsetIndex, pageSize);
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            parameters of the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List nativeQuerySQL(final String sql, final Map<String, ?> params, final int offsetIndex, final int pageSize,
			final Class clazz) {
		return super.getHibernateTemplate().execute(session -> {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append(" Select main.* ");
			queryBuffer.append(" From  ");
			queryBuffer.append(" (Select t.*,rownum rn  ");
			queryBuffer.append("  from ( ");
			queryBuffer.append(sql);
			queryBuffer.append("       ) t  ");
			queryBuffer.append(" ) main ");
			queryBuffer.append(" where main.rn > ");
			queryBuffer.append(offsetIndex);
			queryBuffer.append(" and main.rn <=");
			queryBuffer.append(offsetIndex + pageSize);
			NativeQuery<?> sqlQuery = session.createNativeQuery(queryBuffer.toString());
			sqlQuery.setProperties(params);
			sqlQuery.addEntity(clazz);
			List<?> list = sqlQuery.list();
			loggingCount(list, sql, params);
			return list;

		});
	}

	////////////////////////
	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	@Override
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final List<Object[]> clazz) {
		return nativeQuerySQL(sql, params, null, clazz);
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param param
	 *            the param of query SQL
	 * @return return the result of query
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> sqlQuery = session.createNativeQuery(sql);
			sqlQuery.setProperties(params);
			addTypeMapping(sqlQuery, typeMapping);
			List list = sqlQuery.list();
			loggingCount(list, sql, params);
			return list;
		});
	}

	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	@Override
	public <R> List<R> nativeQuerySQL(final String sql, final Map<String, ?> params,
			final Map<String, Type> typeMapping, final Class<R> clazz) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<R> sqlQuery = session.createNativeQuery(sql, clazz);
			// sqlQuery.addEntity(clazz);
			sqlQuery.setProperties(params);
			addTypeMapping(sqlQuery, typeMapping);

			List<R> list = sqlQuery.list();
			loggingCount(list, sql, params);
			return list;

		});
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            parameters of the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<?> nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping,
			final int offsetIndex, final int pageSize) {
		return super.getHibernateTemplate().execute(session -> {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append(" Select main.* ");
			queryBuffer.append(" From  ");
			queryBuffer.append(" (Select t.*,rownum rn  ");
			queryBuffer.append("  from ( ");
			queryBuffer.append(sql);
			queryBuffer.append("       ) t  ");
			queryBuffer.append(" ) main ");
			queryBuffer.append(" where main.rn > ");
			queryBuffer.append(offsetIndex);
			queryBuffer.append(" and main.rn <=");
			queryBuffer.append(offsetIndex + pageSize);
			NativeQuery<?> sqlQuery = session.createNativeQuery(queryBuffer.toString());
			sqlQuery.setProperties(params);
			addTypeMapping(sqlQuery, typeMapping);
			List<?> list = sqlQuery.list();
			loggingCount(list, sql, params);
			return list;
		});
	}

	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping,
			final List<Object[]> clazz) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> sqlQuery = session.createNativeQuery(sql);
			for (Object[] cla : clazz) {
				sqlQuery.addEntity(String.valueOf(cla[0]), (Class) cla[1]);
			}
			sqlQuery.setProperties(params);
			addTypeMapping(sqlQuery, typeMapping);

			List list = sqlQuery.list();
			loggingCount(list, sql, params);
			return list;

		});
	}

	/**
	 * Execute the update or delete statement.
	 * 
	 * @param sql
	 *            sql
	 * @return The number of entities updated or deleted.
	 */
	@Override
	public int nativeUpdateSQL(final String sql) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> sqlQuery = session.createNativeQuery(sql);
			return sqlQuery.executeUpdate();
		});
	}

	/**
	 * Execute the update or delete statement.
	 * 
	 * @param sql
	 *            native sql
	 * @param param
	 *            the parameters of the native sql
	 * @return return the number of entities updated or deleted.
	 */
	@Override
	public Object nativeUpdateSQL(final String sql, final Map<String, ?> params) {
		return super.getHibernateTemplate().execute(session -> {
			NativeQuery<?> sqlQuery = session.createNativeQuery(sql);
			sqlQuery.setProperties(params);
			return sqlQuery.executeUpdate();
		});
	}

	// 以下函数接口会在将来进行删除，尽量不要使用。

	/**
	 * Parse a original select item hql to a select count hql
	 * 
	 * @param originalHql
	 *            Have a form of "select ... from ... ..." or "from ..."
	 * @return transform to "select count(*) from ... ..."
	 */
	private String parseHqlCount(String originalHql, String distinctName) {

		String loweredOriginalHql = originalHql.toLowerCase();
		int beginPos = loweredOriginalHql.indexOf("from");
		if (beginPos == -1) {
			throw new IllegalArgumentException("not a valid hql string");
		}
		String countField = null;
		if (distinctName != null) {
			countField = "distinct " + distinctName;
		} else {
			countField = "*";
		}

		return "select count(" + countField + ")" + originalHql.substring(beginPos).replaceAll("join fetch ", "join ");
	}

	/**
	 * 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
	 * table2 t2 where..." 开头)
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            参数
	 * @return 返回符合条件的集合
	 */
	@Override
	public List<Map<String, ?>> query(final String hql, final Map<String, ?> params) {
		return query(hql, params, 0, 0);
	}

	/**
	 * 分页查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
	 * table2 t2 where..." 开头)
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @return 返回符合条件的数据集合
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, ?>> query(final String hql, final Map<String, ?> params, final int offsetIndex,
			final int pageSize) {
		return getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(hql);
			query.setProperties(params);
			if (pageSize != 0) {
				query.setFirstResult(offsetIndex);
				query.setMaxResults(pageSize);
			}
			List list = query.list();
			loggingCount(list, hql, params);
			return list;

		});
	}

	/**
	 * 
	 * 根据查询HQL语句返回查询数据库记录
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @param criteria
	 *            Hibernate定义的查询约束条件
	 * @return 符合条件并带有分页的数据库记录
	 */
	@SuppressWarnings({ "rawtypes" })
	public PageResult query(final String hql, final PageRequest criteria) {
		final String innerHql = hql;// DaoUtil.localizeHql(hql);
		List contentList = super.getHibernateTemplate().execute(session -> {

			Query query = session.createQuery(innerHql);
			// query.setFirstResult((criteria.getCurrentPage()-1)*criteria.getPageSize());
			query.setFirstResult(criteria.getCurrentIndex());
			query.setMaxResults(criteria.getPageSize());
			// System.out.println(">>>First
			// Result:"+criteria.getCurrentIndex()+";Max
			// Results:"+criteria.getPageSize());

			Set<Map.Entry<String, Object>> entrySet = criteria.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
				// System.out.println(">>>Parameter:"+entry.getKey()+";Value:"+entry.getValue());
			}
			return query.list();
		});

		Long count = (Long) super.getHibernateTemplate().execute(session -> {
			String countSql = DaoUtil.getCountQL(innerHql);
			Query query = session.createQuery(countSql);
			Set<Map.Entry<String, Object>> entrySet = criteria.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			return query.uniqueResult();
		});
		System.out.println(">>>Result Count:" + contentList.size() + ";Total Count:" + count);
		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentIndex(criteria.getCurrentIndex());
		// result.setCountPage((count.intValue() + criteria.getPageSize() - 1) /
		// criteria.getPageSize());
		result.setTotal(count.intValue());
		return result;
	}

	/**
	 * 根据查询HQL语句返回查询数据库记录
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @param countHql
	 *            对应的查询数据库记录数语句
	 * @param criteria
	 *            Hibernate定义的查询约束条件
	 * @return 符合条件并带有分页的数据库记录
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public PageResult query(final String hql, final String countHql, final PageRequest criteria) {
		final String innerHql = hql;// DaoUtil.localizeHql(hql);
		List contentList = super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(innerHql);
			// query.setFirstResult((criteria.getCurrentPage()-1)*criteria.getPageSize());
			query.setFirstResult(criteria.getCurrentIndex());
			query.setMaxResults(criteria.getPageSize());
			// System.out.println(">>>First
			// Result:"+criteria.getCurrentIndex()+";Max
			// Results:"+criteria.getPageSize());

			Set<Map.Entry<String, Object>> entrySet = criteria.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
				// System.out.println(">>>Parameter:"+entry.getKey()+";Value:"+entry.getValue());
			}
			return query.list();

		});

		Long count = (Long) super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(countHql);
			Set<Map.Entry<String, Object>> entrySet = criteria.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			return query.uniqueResult();

		});
		System.out.println(">>>Result Count:" + contentList.size() + ";Total Count:" + count);
		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentIndex(criteria.getCurrentIndex());
		result.setCountPage((count.intValue() + criteria.getPageSize() - 1) / criteria.getPageSize());
		result.setTotal(count.intValue());
		return result;
	}

	/**
	 * 多表联合分页查询
	 * 
	 * @param queryEntry
	 *            查询的实体
	 * @param fromJoinSubClause
	 *            连接子表
	 * @param whereBodies
	 *            where子句
	 * @param params
	 *            参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * @return 返回符合条件的集合
	 */
	@Override
	public List<Map<String, ?>> queryByHql(final String queryEntry, final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params, final int offsetIndex, final int pageSize,
			final Order[] orders) {

		StringBuffer sb = new StringBuffer();
		sb.append(queryEntry);
		sb.append(" ").append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		sb.append(generateHqlOrderClause(orders));
		String finalHql = sb.toString();

		return query(finalHql, params, offsetIndex, pageSize);
	}

	/**
	 * 支持通过DetachedCriteria来分页查询
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int queryCount(String hql, Map<String, ?> params, String distinctName) {
		hql = parseHqlCount(hql, distinctName);
		List result_list = query(hql, params);
		if (result_list != null && !result_list.isEmpty())
			return ((Long) result_list.get(0)).intValue();
		return 0;
	}

	/**
	 * 查询记录数(同时支持单表，多表)
	 * 
	 * @param fromJoinSubClause
	 *            连接子表查询
	 * @param whereBodies
	 *            where子句
	 * @param params
	 *            参数
	 * @param distinctName
	 *            distinct字段
	 * @return 返回符合条件的记录数
	 */
	@SuppressWarnings("rawtypes")
	// 查询记录数(同时支持单表，多表)
	@Override
	public int queryCount(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
			final String distinctName) {

		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		String finalHql = sb.toString();
		finalHql = parseHqlCount(finalHql, distinctName);

		List result_list = query(finalHql, params);
		return ((Long) result_list.get(0)).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.IDao#remove(java.lang.Object)
	 */
	@Override
	public void remove(T o) {
		super.getHibernateTemplate().delete(o);
	}

	/**
	 * 删除所有符合条件的数据
	 * 
	 * @param hql
	 *            删除语句
	 * @param params
	 *            参数
	 * @return 返回删除成功的记录数
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object removeAllObjects(final String hql, final Map<String, ?> params) {
		return super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(hql);
			query.setProperties(params);
			return query.executeUpdate();
		});
	}

	/**
	 * 通过id删除数据
	 * 
	 * @param id
	 *            要删除的id数据
	 */
	@Override
	public void removeById(Long id) {
		super.getHibernateTemplate().delete(this.findById(id));
	}

	/**
	 * 删除集合数据
	 * 
	 * @param objs
	 *            要删除的集体
	 */
	@Override
	public void removeObjects(Collection<T> objs) {
		super.getHibernateTemplate().deleteAll(objs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.IDao#save(java.lang.Object)
	 */
	@Override
	public Long save(T o) {
		return (Long) super.getHibernateTemplate().save(o);

	}

	/**
	 * 插入操作
	 * 
	 * @param objs
	 *            数据集
	 */
	@Override
	public Collection<T> saveObjects(Collection<T> objs) {
		int i = 0;
		for (T obj : objs) {
			super.getHibernateTemplate().saveOrUpdate(obj);
			if (++i % 20 == 0) {
				super.getHibernateTemplate().flush();
				super.getHibernateTemplate().clear();
			}
		}
		return objs;
	}



	/**
	 * 设置指定Sequence的从新值开始。
	 * 
	 * @param name
	 * @param nextId
	 */
	@Override
	public void setSequence(String name, long nextId) {
		String seq_drop = "drop  sequence " + name;
		String seq_new = "create Sequence " + name + " START WITH " + nextId;

		NativeQuery con = null;

		try {
			con = currentSession().createNativeQuery(seq_drop);
			con.executeUpdate();
			//
			con = currentSession().createNativeQuery(seq_new);
			con.executeUpdate();
		} finally {
		}
	}

	/**
	 * <p>
	 * Updates single data object.
	 * 
	 * @param object
	 *            object to be updated.
	 */
	@Override
	public void update(T o) {
		/*
		 * Date nowDate = new GregorianCalendar().getTime(); if(o instanceof
		 * DataAuditEntityBase){ ((DataAuditEntityBase)o).setLastUpd(nowDate); }
		 */
		super.getHibernateTemplate().update(o);
	}

	/**
	 * 删除所有符合条件的数据
	 * 
	 * @param hql
	 *            删除语句
	 * @param params
	 *            参数
	 * @return 返回删除成功的记录数
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object updateAllObjects(final String hql, final Map<String, ?> params) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		if (params != null) {
			paramss.putAll(params);
		}
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);
		return super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(dhql);
			query.setProperties(paramss);
			return query.executeUpdate();
		});
	}

	/**
	 * Execute update query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName
	 *            the query name is defined in a Hibernate mapping file.
	 * @return the count of updated instance
	 */
	@Override
	public int updateByNamedQuery(String queryName) {
		Session session = super.currentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		return query.executeUpdate();
	}

	/**
	 * Execute update query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName
	 *            The query name is defined in a Hibernate mapping file.
	 * @param params
	 *            The parameters is defined in a Hibernate mapping file.
	 * @return the count of updated instance.
	 */
	@Override
	public int updateByNamedQuery(String queryName, Map<String, Object> params) {
		Session session = super.currentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		query.setProperties(params);
		return query.executeUpdate();
	}

	/**
	 * Execute a named query. A named query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName
	 *            The query name is defined in a Hibernate mapping file.
	 * @param paramName
	 *            The parameter is defined in Hibernate mapping file.
	 * @param value
	 *            The value of parameter name.
	 * @return The count of updated instance.
	 */
	@Override
	public int updateByNamedQuery(String queryName, String paramName, Object value) {
		Session session = super.currentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(queryName);
		query.setParameter(paramName, value);
		return query.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object updateEpKeypartInputs(String sqlStr, Map<String, Object> params) {
		return super.getHibernateTemplate().execute(session -> {
			Query query = session.createQuery(sqlStr);
			query.setProperties(params);
			return query.executeUpdate();

		});
	}

	/**
	 * <p>
	 * Updates a collection of data object.
	 * 
	 * @param objects
	 *            a collection of objects to be updated.
	 */
	@Override
	public void updateObjects(Collection<T> objs) {
		int i = 0;
		for (T obj : objs) {
			super.getHibernateTemplate().saveOrUpdate(obj);
			if (++i % 20 == 0) {
				super.getHibernateTemplate().flush();
				super.getHibernateTemplate().clear();
			}
		}
	}

}