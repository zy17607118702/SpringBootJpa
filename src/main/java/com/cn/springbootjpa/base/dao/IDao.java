
package com.cn.springbootjpa.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.entity.BaseEntity;

@SuppressWarnings("rawtypes")
public interface IDao<T extends BaseEntity,ID extends Serializable> extends JpaRepository<T,ID>{

/**
* 系统序列当前值定义常量
*/
public static String SEQ_CURRVAL_ORACLE = "CURRVAL";


/**
* 系统序列下一个值定义常量
*/
public static String SEQ_NEXTVAL_ORACLE = "NEXTVAL";

/**
* 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
* 
* @param hql 更新语句
* @return 返回更新成功条数
*/
int bulkUpdate(String hql);

/**
* 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
* 
* @param hql    更新语句
* @param params 参数
* @return 返回更新成功条数
*/
int bulkUpdate(final String hql, final Map<String, ?> params);

@Transactional(propagation = Propagation.REQUIRES_NEW)
public int bulkUpdate4Send(final String hql, final Map<String, ?> params);

/**
* Clear current session.
* 
*/
public void clear();

/**
* Close the iterator.
* 
* @param iter The iterator will be close.
*/
public void closeIterator(Iterator iter);

long countByCriteria(DetachedCriteria criteria);

/**
* evict the entity from session so that it will not update
* 
* @param entity
*/
public void evict(Object entity);

/**
* evict the entity from session so that it will not update
* 
* @param entity
*/
public void executeProcedureSql(final String queryString, final Object[] params);

// 单表不分页全部查询(hql语句以 "from table t where..," 开头)
/**
* 单表不分页全部查询(hql语句以 "from table t where..," 开头)
* 
* @param hql    查询语句
* @param params 查询参数
* @return
*/
public List<T> find(final String hql, final Map<String, ?> params);

/**
* 单表分页查询(hql语句以 "from table t where..," 开头)
* 
* @param hql         查询语句
* @param params      查询参数
* @param offsetIndex 查询开始位置
* @param pageSize    页面大小
* @return 返回符合条件的数据集
*/
public List<T> find(final String hql, final Map<String, ?> params, final int offsetIndex, final int pageSize);

@Deprecated
public List<T> find(String hql, Object... paramValues);

/**
* query specifiy objects
* 
* @param example query object
* @return
*/
public List<T> find(T example);

/**
* <p>
* Returns all objects.
* 
* @return all data objects.
*/
public List<T> findAll();

/**
* 支持通过DetachedCriteria来查询
* 
* @param criteria
* @return
*/
List findByCriteria(DetachedCriteria criteria);

/**
* 支持通过DetachedCriteria来分页查询
* 
* @param criteria
* @param firstResult
* @param maxResults
* @return
*/
List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

/**
* 通过field名称和value查询实体信息
* 
* @param fieldName 字段名称
* @param value     字段值
* @return 返回符合条件的实体
*/
public T findByField(String fieldName, String value);

public List<T> findByHql(final String hql, final String[] whereBodies, final Map<String, ?> params);

/**
* 单表联合分页查询
* 
* @param fromJoinSubClause 联合子表查询语句
* @param whereBodies       where子句
* @param params            参数
* @param offsetIndex       查询开始位置
* @param pageSize          页面大小
* @param orders            排序
* @return 返回符合条件的集合
*/
public List<T> findByHql(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
final int offsetIndex, final int pageSize, final Order[] orders);

/**
* <p>
* Finds and returns single data object identified by id.
* <p>
* <b>Notes:<b> Exception throws while specified object not found.
* 
* @param id primary id of data object.
* @return data object.
*/
public T findById(Long id);

public List<T> findByIds(Long... ids);

/**
* Execute a named query. A named query is defined in a Hibernate mapping file.
* 
* @param queryName the name of a Hibernate query in a mapping file
* @return a List containing the results of the query execution
*/
List findByNamedQuery(String queryName);

/**
* Execute a named query. A named query is defined in a Hibernate mapping file.
* 
* @param queryName the name of a Hibernate query in a mapping file
* @param params    the parameters is defined in Hibernate mapping file.
* @return a list containing the result of the query execution.
*/
List findByNamedQuery(String queryName, Map<String, Object> params);

/**
* Execute a named query. A named query is defined in a Hibernate mapping file.
* 
* @param queryName the query name is defined in a Hibernate mapping file.
* @param paramName the parameter is defined in Hibernate mapping file.
* @param value     the value of parameter name.
* @return a list containing the result of the query execution.
*/
List findByNamedQuery(String queryName, String paramName, Object value);

/**
* 返回指定字段值的VO的MAP集合 前提:相应实体类的字段组合作为参数(加上ID字段)的构造函数存在
* 如获取NO(String类型),Descr(String类型)两个字段的值, 则需要提供构造函数T(Long id, String no, String
* descr)
* 
* @param fieldsNames
* @param ids
* @param params
* @return
*/
public Map<Integer, T> findSpecFieldsValueByIds(Collection<String> fieldsNames, Collection<Long> ids,
Map<String, Object> params);

/**
* Flush all pending saves, updates and deletes to the database.
*/
public void flush();

// int bulkUpdate(String hql, Object value);

// int bulkUpdate(String hql, Object[] values);

/**
* <p>
* Finds and returns single data object identified by id.
* 
* @param id primary id of data object.
* @return data object, null if not found.
*/
public T getById(Long id);

Class<T> getEntityClass();

/**
* function: 获取指定Sequence的新值。
* 
* @param sequenceName
* @return
*/
public long getSequence(String sequenceName);

/**
* Execute a HQL, then return the Iterator of the instance.
* 
* @param hql    The HQL will be executed.
* @param params The parameters of the HQL.
* @return The instance of Iterator.
*/
public Iterator iterate(final String hql, final Map<String, ?> params);

/**
* save or updates single data object
* 
* @param o
*/
public void marge(T o);

/**
* find the result for given SQL query
* 
* @param sql          the query SQL
* @param params       the parameters of query SQL
* @param distinctName distinct field name
* @return return list of result
*/
public int nativeQueryCountSQL(final String sql, final Map<String, ?> params, final String distinctName);

/**
* find the result for given SQL query
* 
* @param sql          the query SQL
* @param distinctName distinct field
* @return return the list of result.
*/
public Object nativeQueryCountSQL(final String sql, final String distinctName);

/**
* find the result for given SQL query
* 
* @param the query sql
* @return the list of result
*/
public List<?> nativeQuerySQL(final String sql);

/**
* paging query the result fo given SQL query.
* 
* @param sql         the query SQL
* @param offsetIndex query begin index
* @param pageSize    page size
* @return the list of result
*/
public List<?> nativeQuerySQL(final String sql, final int offsetIndex, final int pageSize);

/**
* find the result for given SQL query
* 
* @param sql   the query SQL
* @param param the param of query SQL
* @return return the result of query
*/
public List<?> nativeQuerySQL(final String sql, final Map<String, ?> param);

/**
* find the result for given SQL query with specify class name
* 
* @param sql    the query SQL
* @param params the params of query SQL
* 
* @param clazz
* 
* @return return the result of query
*/
public List<?> nativeQuerySQL(final String sql, final Map<String, ?> params, final Class clazz);

/**
* paging query the result fo given SQL query.
* 
* @param sql         the query SQL
* @param params      parameters of the query SQL
* @param offsetIndex query begin index
* @param pageSize    page size
* @return the list of result
*/
public List nativeQuerySQL(final String sql, final Map<String, ?> params, final int offsetIndex,
final int pageSize);

public List nativeQuerySQL(final String sql, final Map<String, ?> params, final int offsetIndex, final int pageSize,
final Class clazz);

/**
* find the result for given SQL query with specify class name
* 
* @param sql    the query SQL
* @param params the params of query SQL
* 
* @param clazz
* 
* @return return the result of query
*/
public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final List<Object[]> clazz);

/**
* find the result for given SQL query
* 
* @param sql   the query SQL
* @param param the param of query SQL
* @return return the result of query
*/
public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping);

/**
* find the result for given SQL query with specify class name
* 
* @param sql    the query SQL
* @param params the params of query SQL
* 
* @param clazz
* 
* @return return the result of query
*/
public <R> List<R> nativeQuerySQL(final String sql, final Map<String, ?> params,
final Map<String, Type> typeMapping, final Class<R> clazz);

/**
* paging query the result fo given SQL query.
* 
* @param sql         the query SQL
* @param params      parameters of the query SQL
* @param offsetIndex query begin index
* @param pageSize    page size
* @return the list of result
*/
public List<?> nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping,
final int offsetIndex, final int pageSize);

/**
* find the result for given SQL query with specify class name
* 
* @param sql    the query SQL
* @param params the params of query SQL
* 
* @param clazz
* 
* @return return the result of query
*/
public Object nativeQuerySQL(final String sql, final Map<String, ?> params, final Map<String, Type> typeMapping,
final List<Object[]> clazz);

/**
* Execute the update native sql statement.
* 
* @param sql sql
* @return The number of entities updated or deleted.
*/
public int nativeUpdateSQL(final String sql);

/**
* Execute the update native sql statement.
* 
* @param sql   native sql
* @param param the parameters of the native sql
* @return return the number of entities updated or deleted.
*/
public Object nativeUpdateSQL(final String sql, final Map<String, ?> param);

/**
* 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
* table2 t2 where..." 开头)
* 
* @param hql    查询语句
* @param params 参数
* @return 返回符合条件的集合
*/
public List<Map<String, ?>> query(final String hql, final Map<String, ?> params);

/**
* 分页查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
* table2 t2 where..." 开头)
* 
* @param hql         查询语句
* @param params      参数
* @param offsetIndex 查询开始位置
* @param pageSize    页面大小
* @return 返回符合条件的数据集合
*/
public List<Map<String, ?>> query(final String hql, final Map<String, ?> params, final int offsetIndex,
final int pageSize);

/**
* 多表联合分页查询
* 
* @param queryEntry        查询的实体
* @param fromJoinSubClause 连接子表
* @param whereBodies       where子句
* @param params            参数
* @param offsetIndex       查询开始位置
* @param pageSize          页面大小
* @param orders            排序
* @return 返回符合条件的集合
*/
public List<Map<String, ?>> queryByHql(final String queryEntry, final String fromJoinSubClause,
final String[] whereBodies, final Map<String, ?> params, final int offsetIndex, final int pageSize,
final Order[] orders);

/**
* 查询记录数
* 
* @param hql          查询语句
* @param params       参数
* @param distinctName distinct字段
* @return 返回符合条件的记录数
*/
public int queryCount(String hql, Map<String, ?> params, String distinctName);

/**
* 查询记录数(同时支持单表，多表)
* 
* @param fromJoinSubClause 连接子表查询
* @param whereBodies       where子句
* @param params            参数
* @param distinctName      distinct字段
* @return 返回符合条件的记录数
*/
public int queryCount(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
final String distinctName);

/**
* 删除指定的实体
* 
* @param o 要删除的实体
*/
public void remove(T o);

/**
* 删除所有符合条件的数据
* 
* @param hql    删除语句
* @param params 参数
* @return 返回删除成功的记录数
*/
public Object removeAllObjects(final String hql, final Map<String, ?> params);

/**
* 通过id删除数据
* 
* @param id 要删除的id数据
*/
public void removeById(Long id);

/**
* 删除集合数据
* 
* @param objs 要删除的集体
*/
public void removeObjects(Collection<T> objs);

/**
* 插入操作
* 
* @param o
* @return
*/
public Long save(T o);

/**
* 插入操作
* 
* @param objs 数据集
* @return TODO
*/
public Collection<T> saveObjects(Collection<T> objs);

/**
* 设置指定Sequence的从新值开始。
* 
* @param name
* @param nextId
*/
public void setSequence(String name, long nextId);

/**
* <p>
* Updates single data object.
* 
* @param object object to be updated.
*/
public void update(T o);

/**
* public void marge(T o); public void marge(T o); 更新所有数据
* 
* @param hql    更新语句
* @param params 参数
* @return a result object returned by the action, or null
*/
public Object updateAllObjects(final String hql, final Map<String, ?> params);

/**
* Execute update query is defined in a Hibernate mapping file.
* 
* @param queryName the query name is defined in a Hibernate mapping file.
* @return the count of updated instance
*/
int updateByNamedQuery(String queryName);

/**
* Execute update query is defined in a Hibernate mapping file.
* 
* @param queryName The query name is defined in a Hibernate mapping file.
* @param params    The parameters is defined in a Hibernate mapping file.
* @return the count of updated instance.
*/
int updateByNamedQuery(String queryName, Map<String, Object> params);

/**
* Execute a named query. A named query is defined in a Hibernate mapping file.
* 
* @param queryName The query name is defined in a Hibernate mapping file.
* @param paramName The parameter is defined in Hibernate mapping file.
* @param value     The value of parameter name.
* @return The count of updated instance.
*/
int updateByNamedQuery(String queryName, String paramName, Object value);

public Object updateEpKeypartInputs(String sqlStr, Map<String, Object> params);

/**
* <p>
* Updates a collection of data object.
* 
* @param objects a collection of objects to be updated.
*/
public void updateObjects(Collection<T> objs);
}