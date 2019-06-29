
package com.cn.springbootjpa.base.bo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.common.page.SpecificationUtil;
import com.cn.springbootjpa.base.entity.BaseEntity;

public abstract class BaseBoImpl<T extends BaseEntity, ID extends Serializable> implements BaseBo<T, ID> {

	private static final Map<Class<?>, ExampleMatcher> modelDefaultMatcher = new HashMap<>();

	@Override
	public T save(T entity) {
		return getDao().save(entity);
	}

	@Override
	public Iterable<T> save(Iterable<T> entities) {
		return getDao().saveAll(entities);
	}

	@Override
	public void delete(ID id) {
		getDao().deleteById(id);
	}

	@Override
	public void delete(T entity) {
		getDao().delete(entity);
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		getDao().deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		getDao().deleteAll();
	}

	/**
	 * Optional Java新特性 可以用Optional里面的方法判断是否为空 
	 * isPresent();//判断是否为空
	 * ifPresent(Consumer<? super T> consumer) ;//如果value不为null，则执行consumer式的函数，为null不做事
	 */
	@Override
	public Optional<T> getById(ID id) {
		return getDao().findById(id);
	}

	@Override
	public boolean exists(ID id) {
		return getDao().existsById(id);
	}

	@Override
	public boolean exists(T example) {
		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<T> of = Example.of(example, match);
		return exists(of);
	}

	private ExampleMatcher getDefaultMatch(Class<? extends Object> clazz) {
		if (!modelDefaultMatcher.containsKey(clazz)) {
			modelDefaultMatcher.put(clazz, genDefaultMatcher(clazz));
		}
		ExampleMatcher match = modelDefaultMatcher.get(clazz);
		return match;
	}

	private ExampleMatcher genDefaultMatcher(Class<? extends Object> class1) {
		Field[] declaredFields = class1.getDeclaredFields();
		ExampleMatcher match = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING);
		for (Field field : declaredFields) {
			StringMatcherType annotation = field.getAnnotation(StringMatcherType.class);
			if (annotation != null) {
				match = match.withMatcher(field.getName(), GenericPropertyMatcher.of(annotation.value()));
			}
		}

		return match;
	}

	@Override
	public boolean exists(Example<T> example) {
		return getDao().exists(example);
	}

	@Override
	public long count() {
		return getDao().count();
	}

	@Override
	public long count(T example) {
		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<T> of = Example.of(example, match);
		return count(of);
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		return getDao().count(example);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return getDao().findAll(sort);
	}

	@Override
	public List<T> findAll(Iterable<ID> ids) {
		return getDao().findAllById(ids);
	}

	@Override
	public List<T> findAll(T example) {
		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<T> of = Example.of(example, match);
		return findAll(of);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		return getDao().findAll(example);
	}

	@Override
	public <S extends T> List<S> findAll(S example, Sort sort) {
		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<S> of = Example.of(example, match);
		return findAll(of, sort);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return getDao().findAll(example, sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return getDao().findAll(pageable);
	}

	@Override
	public <S extends T> Page<S> findAll(S example, Pageable pageable) {
		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<S> of = Example.of(example, match);
		return findAll(of, pageable);
	}

	@Override
	public Page<T> findAll(T example, List<QueryCondition> conditions, Pageable pageable) {
		if (conditions == null || conditions.size() == 0) {
			return findAll(example, pageable);
		}

		Specification<T> c2s = SpecificationUtil.c2s(conditions);

		ExampleMatcher match = getDefaultMatch(example.getClass());
		Example<T> of = Example.of(example, match);
		Specification<T> result = new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = QueryByExamplePredicateBuilder.getPredicate(root, cb, of);
				Predicate and = cb.and(predicate, c2s.toPredicate(root, query, cb)); // predicate放在前面，可能出现的1=1在前面
				return and;
			}

		};
		return getDao().findAll(result, pageable);
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return getDao().findAll(example, pageable);
	}

	@Override
	public boolean exists(List<QueryCondition> conditions) {
		return count(conditions) > 0;
	}

	@Override
	public long count(List<QueryCondition> conditions) {
		Specification<T> c2s = SpecificationUtil.c2s(conditions);

		return getDao().count(c2s);
	}

	@Override
	public List<T> findAll(List<QueryCondition> conditions) {
		Specification<T> c2s = SpecificationUtil.c2s(conditions);

		return getDao().findAll(c2s);
	}

	@Override
	public List<T> findAll(List<QueryCondition> conditions, Sort sort) {
		Specification<T> c2s = SpecificationUtil.c2s(conditions);

		return getDao().findAll(c2s, sort);
	}

	@Override
	public Page<T> findAll(List<QueryCondition> conditions, Pageable pageable) {
		Specification<T> c2s = SpecificationUtil.c2s(conditions);

		return getDao().findAll(c2s, pageable);
	}
}
