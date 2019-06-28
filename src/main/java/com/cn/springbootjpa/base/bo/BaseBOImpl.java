
package com.cn.springbootjpa.base.bo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.dao.IDao;

/**
 * <p>
 * BaseBoImpl is the superclass of all business object class.
 * 
 * @author Danne
 * @param <T> parameterized class.
 */
@Service
@Transactional
public abstract class BaseBOImpl<T> implements BaseBO<T> {

    protected Log log = LogFactory.getLog(this.getClass());
    /**
     * @deprecated use getDao() instead.
     */
    @Deprecated
    protected IDao<T> dao;

    /**
     * <p>
     * Creates a collection of data object.
     * 
     * @param objects a collection of objects to be created.
     * @return a collection of objects just created.
     */
    @Override
    public Collection<T> create(Collection<T> o_list) {
        getDao().saveObjects(o_list);
        return o_list;
    }

    /**
     * <p>
     * Creates a single data object.
     * 
     * @param object an object to be created.
     * @return an object just created.
     */
    @Override
    public T create(T o) {
        getDao().save(o);
        return o;
    }

    /**
     * <p>
     * Deletes a collection of data objects.
     * 
     * @param objects an collcetion of objects to be deleted.
     */
    @Override
    public void delete(Collection<T> o_list) {
        getDao().removeObjects(o_list);
    }

    /**
     * <p>
     * Deletes single data object identified by id.
     * 
     * @param id primary id of data object.
     */
    @Override
    public void delete(Long id) {
        getDao().removeById(id);
    }

    /**
     * <p>
     * Deletes single data object identified by id.
     * 
     * @param object object to be deleted.
     */
    @Override
    public void delete(T o) {
        getDao().remove(o);
    }

    /**
     * <p>
     * Executes a deleting operation with specified hql and params.
     * 
     * @param hql    HQL(hibernate query language) string.
     * @param params paramters value applied to HQL.
     */
    @Override
    public void deleteAll(final String hql, final Map<String, ?> params) {
        getDao().removeAllObjects(hql, params);
    }

    /**
     * <p>
     * Finds and returns single data object identified by id.
     * <p>
     * <b>Notes:<b> Exception throws while specified object not found.
     * 
     * @param id primary id of data object.
     * @return data object.
     */
    @Override
    public T find(Long id) {
        return getDao().findById(id);
    }

    /**
     * <p>
     * Executes a finding objects operation with specified hql and params.
     * 
     * @param hql    HQL(hibernate query language) string.
     * @param params paramters value applied to HQL.
     * @return a list of result data objects.
     */
    @Override
    public List<T> find(String hql, Map<String, ?> params) {
        return getDao().find(hql, params);
    }

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
    @Override
    public List<T> find(String hql, Map<String, ?> params, int offsetIndex, int pageSize) {
        return getDao().find(hql, params, offsetIndex, pageSize);
    }

    /**
     * query specifiy objects
     * 
     * @param example query object
     * @return
     */
    @Override
    public List<T> find(T example) {
        return getDao().find(example);
    }

    /**
     * <p>
     * Returns all objects.
     * 
     * @return all data objects.
     */
    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    /**
     * 支持通过DetachedCriteria查询数据
     * 
     * @param criteria DetachedCriteria实例
     * @return 返回查询结果集
     */
    @Override
    public List findByCriteria(DetachedCriteria criteria) {
        return getDao().findByCriteria(criteria);
    }

    /**
     * 支持通过DetachedCriteria分页查询数据
     * 
     * @param criteria DetachedCriteria实例
     * @return 返回查询结果集
     */
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getDao().findByCriteria(criteria, firstResult, maxResults);
    }

    public long countByCriteria(DetachedCriteria criteria) {
        return getDao().countByCriteria(criteria);
    }

    /**
     * 通过HQL查询数据
     * 
     * @param fromJoinSubClause FROM子句
     * @param whereBodies       WHERE子句
     * @param params            参数
     * @param offsetIndex       查询开始位置
     * @param pageSize          每页容量
     * @param orders            查询排序
     * @return 返回所需数据集合
     */
    @Override
    public List<T> findByHql(String fromJoinSubClause, String[] whereBodies, Map<String, ?> params, int offsetIndex,
            int pageSize, Order[] orders) {
        return getDao().findByHql(fromJoinSubClause, whereBodies, params, offsetIndex, pageSize, orders);
    }

    /**
     * 查询数据，通过hbm.xml文件中配置的查询语句查询数据
     * 
     * @param queryName 查询字段名字
     * @return 返回符合查询条件的结果集
     */
    @Override
    public List findByNamedQuery(String queryName) {

        return getDao().findByNamedQuery(queryName);
    }

    /**
     * 查询数据，通过hbm.xml文件中配置的查询语句和参数条件查询数据
     * 
     * @param queryName 配置文件中的查询语句
     * @param params    配置文件中配置的查询参数
     * @return 返回符合条件的数据集合
     */
    @Override
    public List findByNamedQuery(String queryName, Map<String, Object> params) {
        return getDao().findByNamedQuery(queryName, params);
    }

    /**
     * 查询符合条件的集合，通过*.HBM.XML配置文件中的查询语句和参数进行查询
     * 
     * @param queryName 配置文件中的查询语句
     * @param name      查询参数
     * @param value     查询参数
     * @return 返回符合查询条件的数据集合
     */
    @Override
    public List findByNamedQuery(String queryName, String name, Object value) {
        return getDao().findByNamedQuery(queryName, name, value);
    }

    /**
     * <p>
     * Finds and returns single data object identified by id.
     * 
     * @param id primary id of data object.
     * @return data object, null if not found.
     */
    @Override
    public T getById(Long id) {
        return getDao().getById(id);
    }

    /**
     * <p>
     * Returns the instance of IDao conntected to this business object.
     * 
     * @return an instance of IgetDao().
     */
    @Override
    public IDao<T> getDao() {
        if (this.dao != null)
            return this.dao;
        throw new IllegalStateException("Subclass must implements this method to return dao instance.");
    }

    /**
     * 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
     * table2 t2 where..." 开头)
     * 
     * @param hql    查询语句
     * @param params 查询参数
     * @return 返回符合条件的MAP
     */
    @Override
    public List<Map<String, ?>> query(String hql, Map<String, ?> params) {
        return getDao().query(hql, params);
    }

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
    @Override
    public List<Map<String, ?>> queryByHql(String queryEntry, String fromJoinSubClause, String[] whereBodies,
            Map<String, ?> params, int offsetIndex, int pageSize, Order[] orders) {
        return getDao().queryByHql(queryEntry, fromJoinSubClause, whereBodies, params, offsetIndex, pageSize, orders);
    }

    /**
     * HQL查询记录数
     * 
     * @param hql          HQL语句
     * @param params       HQL参数
     * @param distinctName DISTINCT字段
     * @return 返回符合条件的记录数
     */
    @Override
    public int queryCount(final String hql, final Map<String, ?> params, String distinctName) {
        return getDao().queryCount(hql, params, distinctName);
    }

    /**
     * 根据查询语句获取该结果的数量，用于翻页
     * 
     * @param fromJoinSubClause
     * @param whereBodies
     * @param params
     * @param distinctName
     * @return
     */
    @Override
    public int queryCount(String fromJoinSubClause, String[] whereBodies, Map<String, ?> params, String distinctName) {
        return getDao().queryCount(fromJoinSubClause, whereBodies, params, distinctName);
    }

    /**
     * <p>
     * Updates a collection of data object.
     * 
     * @param objects a collection of objects to be updated.
     */
    @Override
    public void update(Collection<T> o_list) {
        getDao().updateObjects(o_list);
    }

    /**
     * @Override public void marge(T o) { getDao().marge(o); }
     *           <p>
     *           Updates single data object.
     * 
     * @param object object to be updated.
     */
    @Override
    public void update(T o) {
        getDao().update(o);
    }

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @return 返回更新成功条数
     */
    @Override
    public int updateByNamedQuery(String queryName) {
        return getDao().updateByNamedQuery(queryName);
    }

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param params    更新参数
     * @return 返回更新成功条数
     */
    @Override
    public int updateByNamedQuery(String queryName, Map<String, Object> params) {
        return getDao().updateByNamedQuery(queryName, params);
    }

    /**
     * 通过配置文件中更新语句更新数据
     * 
     * @param queryName 更新语句
     * @param name
     * @param value
     * @return 返回更新成功条数
     */
    @Override
    public int updateByNamedQuery(String queryName, String name, Object value) {
        return getDao().updateByNamedQuery(queryName, name, value);
    }

}