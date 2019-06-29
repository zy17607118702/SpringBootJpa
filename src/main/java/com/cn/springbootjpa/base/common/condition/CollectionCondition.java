package com.cn.springbootjpa.base.common.condition;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cn.springbootjpa.base.common.page.CompareExpression;

public class CollectionCondition<T> implements ICondition {

	private String field;
	private Collection<T> value;
	private CompareExpression ex;

	public CollectionCondition() {

	}

	public CollectionCondition(String f, Collection<T> v, CompareExpression express) {
		field = f;
		value = v;
		ex = express;
	}

	public static <T> ICondition in(String field, Collection<T> value) {
		return new CollectionCondition<>(field, value, CompareExpression.In);
	}

	@Override
	public ICondition copy() {
		return new CollectionCondition<>(field, value, ex);
	}

	@Override
	public String toStrCondition() {
		switch (ex) {
		case In:
			String collect = value.stream().map(item -> getValueStr(item)).collect(Collectors.joining(",", "(", ")"));
			return field + " IN " + collect;

		default:
			break;
		}
		return null;
	}

	@Override
	public Specification<T> c2s() {
		Specification<T> result = new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<T> fieldPath = root.get(field);
				switch (ex) {
				case In:
					In<T> in = cb.in(fieldPath);
					for (T t : value) {
						in.value(t);
					}

					return in;

				default:
					break;
				}
				return null;
			}
		};

		return result;
	}
}
