package com.cn.springbootjpa.base.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author LiuYanLu ISimpleDao的实现类
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository("simpleDao")
public class SimpleDaoImpl extends HibernateDaoSupport implements ISimpleDao {

	@Autowired
	@Qualifier("baseDao")
	private IDao dao = null;

	/**
	 * 日志工具
	 */
	protected final Log log = LogFactory.getLog(getClass());

	public SimpleDaoImpl() {
	}

	/**
	 * 关联Hibernate SessionFactory
	 * 
	 * @param sessionFactory
	 *            Hibernate SessionFactory
	 */
	@Autowired
	public void setHibernateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#find(java.lang.Class,
	 * java.lang.String, java.util.Map)
	 */
	@Override

	public <T> List<T> find(Class<T> T, final String hql, final Map<String, ?> params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							query.setParameter(entry.getKey(), value);
						}
					}
				}
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#findAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> findAll(Class<T> T) {
		return super.getHibernateTemplate().loadAll(T);
	}

	// 单表分页查询
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#findByHql(java.lang.Class,
	 * java.lang.String, java.lang.String[], java.util.Map, int, int,
	 * org.hibernate.criterion.Order[])
	 */
	@Override
	public <T> List<T> findByHql(Class<T> T, final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex, final int pageSize, final Order[] orders) {

		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				StringBuffer sb = new StringBuffer();
				sb.append(fromJoinSubClause);

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

				if (orders != null) {
					sb.append(generateHqlOrderClause(orders));
				}
				String finalHql = sb.toString();

				Query query = session.createQuery(finalHql);
				if (params != null) {
					for (Entry<String, ?> entry : params.entrySet()) {
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Object[]) {
								query.setParameterList(entry.getKey(), (Object[]) value);

							} else if (value instanceof Collection<?>) {
								query.setParameterList(entry.getKey(), (Collection<?>) value);

							} else {
								query.setParameter(entry.getKey(), value);
							}

						}
					}
				}

				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}

				List result = query.list();
				return result;
			}
		});
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#findById(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public <T> T findById(Class<T> T, Long id) {
		return getHibernateTemplate().load(T, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#query(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, ?>> query(final String hql, final Map<String, ?> params) {
		return (List<Map<String, ?>>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(final Session session) {
				final Query query = session.createQuery(hql);
				if (params != null) {
					for (final Entry<String, ?> entry : params.entrySet()) {
						final Object value = entry.getValue();
						if (value != null) {
							query.setParameter(entry.getKey(), value);
						}
					}
				}
				return query.list();
			}
		});

	}

	/**
	 * get the param name from a where body
	 * 
	 * @param ori
	 *            like "id = :idinput"
	 * @return "idinput"
	 */
	protected String getWhereBodyParamName(String ori) {
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
	 * 根据输入的Hibernate Order数组生成对应的排序HQL语句
	 * 
	 * @param orders
	 *            Hibernate Order数组
	 * @return 生成的排序HQL语句
	 */
	protected String generateHqlOrderClause(Order[] orders) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saicmotor.framework.dao.ISimpleDao#find(java.lang.Class,
	 * java.lang.String, java.util.Map, int, int)
	 */
	@Override
	public <T> List<T> find(Class<T> T, final String hql, final Map<String, ?> params, final int offsetIndex,
			final int pageSize) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				query.setProperties(params);
				if (pageSize != 0) {
					query.setFirstResult(offsetIndex);
					query.setMaxResults(pageSize);
				}
				return query.list();
			}
		});
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
	@Override
	// 查询记录数(同时支持单表，多表)
	public int queryCount(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
			final String distinctName) {
		return dao.queryCount(fromJoinSubClause, whereBodies, params, distinctName);
	}
}