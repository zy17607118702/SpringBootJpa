package com.cn.springbootjpa.base.common.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SingleCondition<T> implements ICondition {

	private String f;
	private T value;
	private CompareExpression express;

	public SingleCondition() {

	}

	public SingleCondition(String field, T value, CompareExpression ex) {
		f = field;
		this.value = value;
		this.express = ex;
	}

	public static <T> ICondition eq(String field, T value) {
		SingleCondition<T> singleCondition = new SingleCondition<>(field, value, CompareExpression.Eq);

		return singleCondition;
	}

	public static <T> ICondition notEq(String field, T value) {
		SingleCondition<T> singleCondition = new SingleCondition<>(field, value, CompareExpression.NotEq);

		return singleCondition;
	}

	public static ICondition isNull(String field) {
		SingleCondition<?> singleCondition = new SingleCondition<>(field, null, CompareExpression.IsNull);

		return singleCondition;
	}

	public static ICondition isNotNull(String field) {
		SingleCondition<?> singleCondition = new SingleCondition<>(field, null, CompareExpression.NotNull);

		return singleCondition;
	}

	public static ICondition like(String field, String value) {
		SingleCondition<?> singleCondition = new SingleCondition<>(field, value, CompareExpression.Like);

		return singleCondition;
	}

	public static ICondition likeBegin(String field, String value) {
		SingleCondition<?> singleCondition = new SingleCondition<>(field, value, CompareExpression.LikeBegin);

		return singleCondition;
	}

	public static ICondition likeEnd(String field, String value) {
		SingleCondition<?> singleCondition = new SingleCondition<>(field, value, CompareExpression.LikeEnd);

		return singleCondition;
	}

	@Override
	public ICondition copy() {
		SingleCondition<T> newCon = new SingleCondition<T>(f, value, express);

		return newCon;
	}

	@Override
	public String toStrCondition() {
		String exStr = "=";
		switch (express) {
		case Eq:
			exStr = "=";
			break;
		case NotEq:
			exStr = "<>";
			break;
		case IsNull:
			return f + " IS NULL";
		case NotNull:
			return f + " IS NOT NULL";
		case Like:
			return f + " LIKE '%" + value + "%'";
		case LikeBegin:
			return f + " LIKE '" + value + "%'";
		case LikeEnd:
			return f + " LIKE '%" + value + "'";
		default:
			break;
		}
		return f + " " + exStr + " " + getValueStr(value);
	}

	@Override
	public Specification<T> c2s() {
		Specification<T> result = new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<T> fieldPath = root.get(f);
				switch (express) {
				case Eq:
					return cb.equal(fieldPath, value);
				case NotEq:
					return cb.notEqual(fieldPath, value);
				case IsNull:
					return cb.isNull(fieldPath);
				case NotNull:
					return cb.isNotNull(fieldPath);
				case Like:
					return cb.like(fieldPath.as(String.class), "%" + value + "%");
				case LikeBegin:
					return cb.like(fieldPath.as(String.class), value + "%");
				case LikeEnd:
					return cb.like(fieldPath.as(String.class), "%" + value);
				default:
					break;
				}

				return null;
			}

		};

		return result;
	}
}
