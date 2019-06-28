
package com.cn.springbootjpa.base.dao;

// import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

/**
 * @author Administrator this class define the dao for simple.
 */
public interface ISimpleDao {

	/**
	 * 查找对象
	 * 
	 * @param <T>
	 *            模板对象类
	 * @param T
	 *            模板对象
	 * @return 符合条件的数据库记录列表
	 */
	public <T> List<T> findAll(Class<T> T);

	/**
	 * 根据指定数据库ID查找对象
	 * 
	 * @param <T>
	 *            模板对象类
	 * @param T
	 *            模板对象
	 * @param id
	 *            指定的数据库ID
	 * @return 符合条件的数据库记录列表
	 */
	public <T> T findById(Class<T> T, Long id);

	// 单表不分页全部查询(hql语句以 "from table t where..," 开头)
	/**
	 * 
	 * 单表不分页全部查询(hql语句以 "from table t where..," 开头)
	 * 
	 * @param <T>
	 *            模板对象类
	 * @param T
	 *            模板对象
	 * @param hql
	 *            查询用HQL语句
	 * @param params
	 *            HQL语句对应参数变量
	 * @return 符合条件的数据库记录列表
	 */
	public <T> List<T> find(Class<T> T, final String hql, final Map<String, ?> params);

	//
	/**
	 * 分页查询
	 * 
	 * @param <T>
	 *            模板对象类
	 * @param T
	 *            模板对象
	 * @param hql
	 *            查询用HQL语句
	 * @param params
	 *            HQL语句对应参数变量
	 * @param offsetIndex
	 *            分页查询的起始记录坐标
	 * @param pageSize
	 *            最大的返回查询记录数
	 * @return 符合条件的数据库记录列表
	 */
	public <T> List<T> find(Class<T> T, final String hql, final Map<String, ?> params, final int offsetIndex,
			final int pageSize);

	// 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1
	// t1, table2 t2 where..." 开头)
	/**
	 * 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
	 * table2 t2 where..." 开头)
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @param params
	 *            HQL语句对应参数变量
	 * @return 符合条件的数据库记录列表
	 */
	public List<Map<String, ?>> query(final String hql, final Map<String, ?> params);

	// 单表联合分页查询
	/**
	 * 单表联合分页查询
	 * 
	 * @param <T>
	 *            模板对象类
	 * @param T
	 *            模板对象
	 * @param fromJoinSubClause
	 *            查询用HQL语句的“from”部分
	 * @param whereBodies
	 *            查询用HQL语句的"where"部分
	 * @param params
	 *            HQL语句对应参数变量
	 * @param offsetIndex
	 *            分页查询的起始记录坐标
	 * @param pageSize
	 *            最大的返回查询记录数
	 * @param orders
	 *            查询用HQL语句的"order"排序部分
	 * @return 符合条件的数据库记录列表
	 */
	public <T> List<T> findByHql(Class<T> T, final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex, final int pageSize, final Order[] orders);

	public int queryCount(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
			final String distinctName);
}