
package com.cn.springbootjpa.base.bo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.dao.IDao;

/**
 * <p>
 * BaseBo interface defines a series of convenient methods for business logic
 * operation.
 * <p>
 * BO(Business Object) is the basic unit of java object contains real business
 * logic code. This interface is the superinterface of all business object
 * interface.
 * 
 * @author Danne
 * @param <T> parameterized class.
 */
@Transactional
@SuppressWarnings("rawtypes")
public interface BaseBO<T> {

    /**
     * <p>
     * Creates a collection of data object.
     * 
     * @param objects a collection of objects to be created.
     * @return a collection of objects just created.
     */
    public Collection<T> create(Collection<T> o_list);

    /**
     * <p>
     * Creates a single data object.
     * 
     * @param object an object to be created.
     * @return an object just created.
     */
    public T create(T o);

    /**
     * <p>
     * Deletes a collection of data objects.
     * 
     * @param objects an collcetion of objects to be deleted.
     */
    public void delete(Collection<T> o_list);

    /**
     * <p>
     * Deletes single data object identified by id.
     * 
     * @param id primary id of data object.
     */
    public void delete(Long id);

    /**
     * <p>
     * Deletes single data object identified by id.
     * 
     * @param object object to be deleted.
     */
    public void delete(T o);

    /**
     * <p>
     * Executes a deleting operation with specified hql and params.
     * 
     * @param hql    HQL(hibernate query language) string.
     * @param params paramters value applied to HQL.
     */
    public void deleteAll(final String hql, final Map<String, ?> params);

    /**
     * <p>
     * Finds and returns single data object identified by id.
     * <p>
     * <b>Notes:<b> Exception throws while specified object not found.
     * 
     * @param id primary id of data object.
     * @return data object.
     */
    public T find(Long id);

    /**
     * 根据查询语句获取结果
     * 
     * @param hql
     * @param params
     * @return
     */
    public List<T> find(final String hql, final Map<String, ?> params);

    /**
     * <p>
     * Executes a finding objects operation with specified hql and params, then
     * returns those part of objects with specified offset index and fetch page size
     * from result set.
     * 
     * @param hql         HQL(hibernate query language) string.
     * @param params      paramters value applied to HQL.
     * @param offsetIndex offset index from result set.
     * @param pageSize    fetch size from result set.
     * @return a list of result data objects.
     */
    public List<T> find(String hql, Map<String, ?> params, int offsetIndex, int pageSize);

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
     * 支持通过DetachedCriteria查询数据
     * 
     * @param criteria DetachedCriteria实例
     * @return 返回查询结果集
     */
    public List findByCriteria(DetachedCriteria criteria);

    /**
     * 支持通过DetachedCriteria分页查询数据
     * 
     * @param criteria DetachedCriteria实例
     * @return 返回查询结果集
     */
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    public long countByCriteria(DetachedCriteria criteria);

    /**
     * HQL分页查询实体数据
     * 
     * @param queryEntry        实体名
     * @param fromJoinSubClause 连接子对象
     * @param whereBodies       WHERE子句
     * @param params            查询参数
     * @param offsetIndex       查询开始位置
     * @param pageSize          每页容量
     * @param orders            排序
     * @return 返回符合条件的结果集
     */
    public List<T> findByHql(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
            final int offsetIndex, final int pageSize, final Order[] orders);
    //
    // public List<T> findByIds(Long[] ids);
    //
    // public List<T> findByQuery(Map<String, Object> query, int i, int pageSize);
    //
    // public boolean exists(Long id);
    //
    // public int enable(boolean enabled, Long[] ids);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @return 返回更新成功条数
     */
    public List findByNamedQuery(String queryName);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param params    更新参数
     * @return 返回更新成功条数
     */
    public List findByNamedQuery(String queryName, Map<String, Object> params);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param name
     * @param value
     * @return 返回更新成功条数
     */
    public List findByNamedQuery(String queryName, String name, Object value);

    /**
     * <p>
     * Finds and returns single data object identified by id.
     * 
     * @param id primary id of data object.
     * @return data object, null if not found.
     */
    public T getById(Long id);

    /**
     * <p>
     * Creates a collection of data object.
     * 
     * @param objects a collection of objects to be created.
     * @return a collection of objects just created.
     */
    public IDao<T> getDao();

    /**
     * 根据查询语句获取该结果的数量，用于翻页
     * 
     * @param fromJoinSubClause
     * @param whereBodies
     * @param params
     * @param distinctName
     * @return
     */
    public List<Map<String, ?>> query(final String hql, final Map<String, ?> params);

    /**
     * HQL分页查询实体数据
     * 
     * @param queryEntry        实体名
     * @param fromJoinSubClause 连接子对象
     * @param whereBodies       WHERE子句
     * @param params            查询参数
     * @param offsetIndex       查询开始位置
     * @param pageSize          每页容量
     * @param orders            排序
     * @return 返回符合条件的结果集
     */
    public List<Map<String, ?>> queryByHql(final String queryEntry, final String fromJoinSubClause,
            final String[] whereBodies, final Map<String, ?> params, final int offsetIndex, final int pageSize,
            final Order[] orders);

    /**
     * HQL查询记录数
     * 
     * @param hql          HQL语句
     * @param params       HQL参数
     * @param distinctName DISTINCT字段
     * @return 返回符合条件的记录数
     */
    public int queryCount(final String hql, final Map<String, ?> params, String distinctName);

    /**
     * 根据查询语句获取该结果的数量，用于翻页
     * 
     * @param fromJoinSubClause
     * @param whereBodies
     * @param params
     * @param distinctName
     * @return
     */
    public int queryCount(final String fromJoinSubClause, final String[] whereBodies, final Map<String, ?> params,
            final String distinctName);

    /**
     * <p>
     * Updates a collection of data object.
     * 
     * @param objects a collection of objects to be updated.
     */
    public void update(Collection<T> o_list);

    /**
     * <p>
     * Updates single data object.
     * 
     * @param object object to be updated.
     */
    public void update(T o);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @return 返回更新成功条数
     */
    public int updateByNamedQuery(String queryName);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param name
     * @param value
     * @return 返回更新成功条数
     */
    public int updateByNamedQuery(String queryName, Map<String, Object> params);

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param params    更新参数
     * @return 返回更新成功条数
     */
    public int updateByNamedQuery(String queryName, String name, Object value);

}