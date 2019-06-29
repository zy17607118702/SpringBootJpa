package com.cn.springbootjpa.base.common.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cn.springbootjpa.base.common.page.CompareExpression;

public class ComparableCondition<T extends Comparable<? super T>> implements ICondition {

	private String f;
	private T v;
	private CompareExpression ex;

	public ComparableCondition() {

	}

	public ComparableCondition(String field, T value, CompareExpression express) {
		f = field;
		v = value;
		ex = express;
	}

	public static <Y extends Comparable<? super Y>> ICondition gt(String field, Y value) {
		ComparableCondition<Y> comparableCondition = new ComparableCondition<>(field, value, CompareExpression.Gt);

		return comparableCondition;
	}

	public static <Y extends Comparable<? super Y>> ICondition ge(String field, Y value) {
		ComparableCondition<Y> comparableCondition = new ComparableCondition<>(field, value, CompareExpression.Ge);

		return comparableCondition;
	}

	public static <Y extends Comparable<? super Y>> ICondition le(String field, Y value) {
		ComparableCondition<Y> comparableCondition = new ComparableCondition<>(field, value, CompareExpression.Le);

		return comparableCondition;
	}

	public static <Y extends Comparable<? super Y>> ICondition lt(String field, Y value) {
		ComparableCondition<Y> comparableCondition = new ComparableCondition<>(field, value, CompareExpression.Lt);

		return comparableCondition;
	}

	@Override
	public String toStrCondition() {
		String opt = "=";
		switch (ex) {
		case Gt:
			opt = ">";
			break;
		case Ge:
			opt = ">=";
			break;
		case Lt:
			opt = "<";
			break;
		case Le:
			opt = "<=";
			break;

		default:
			break;
		}
		return f + opt + getValueStr(v);
	}

	@Override
	public Specification<T> c2s() {
		Specification<T> result = new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<T> fieldPath = root.get(f);
				switch (ex) {
				case Gt:
					return cb.greaterThan(fieldPath, v);
				case Ge:
					return cb.greaterThanOrEqualTo(fieldPath, v);
				case Lt:
					return cb.lessThan(fieldPath, v);
				case Le:
					return cb.lessThanOrEqualTo(fieldPath, v);
				default:
					break;
				}

				return null;
			}

		};

		return result;
	}

	@Override
	public ICondition copy() {
		ComparableCondition<T> comparableCondition = new ComparableCondition<>(f, v, ex);

		return comparableCondition;
	}

}
