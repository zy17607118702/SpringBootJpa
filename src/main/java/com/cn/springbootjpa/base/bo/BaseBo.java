
package com.cn.springbootjpa.base.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.base.entity.BaseEntity;

/**
 * 标准service层查询接口 中间直接用类型的查询，需要类型T的属性不用基础类型，都用包装类型，这样才能够进行正确的查询
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseBo<T extends BaseEntity, ID extends Serializable> {

	BaseDao<T, ID> getDao();

	T save(T entity);

	 Iterable<T> save(Iterable<T> entities);

	void delete(ID id);

	void delete(T entity);

	void deleteInBatch(Iterable<T> entities);

	void deleteAll();

	Optional<T> getById(ID id);

	boolean exists(ID id);

	/**
	 * 将T对象中的不为null的属性按与进行默认查询
	 * 
	 * @param example
	 * @return
	 */
	boolean exists(T example);

	boolean exists(Example<T> example);

	long count();

	/**
	 * 将T对象中的不为null的属性按与进行默认查询
	 * 
	 * @param example
	 * @return
	 */
	long count(T example);

	<S extends T> long count(Example<S> example);

	List<T> findAll();

	List<T> findAll(Sort sort);

	List<T> findAll(Iterable<ID> ids);

	List<T> findAll(T example);

	<S extends T> List<S> findAll(Example<S> example);

	<S extends T> List<S> findAll(S example, Sort sort);

	<S extends T> List<S> findAll(Example<S> example, Sort sort);

	Page<T> findAll(Pageable pageable);

	<S extends T> Page<S> findAll(S example, Pageable pageable);

	<S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

	long count(List<QueryCondition> conditions);

	List<T> findAll(List<QueryCondition> conditions);

	List<T> findAll(List<QueryCondition> conditions, Sort sort);

	Page<T> findAll(List<QueryCondition> conditions, Pageable pageable);

	boolean exists(List<QueryCondition> conditions);

	public Page<T> findAll(T example, List<QueryCondition> conditions, Pageable pageable);
}
