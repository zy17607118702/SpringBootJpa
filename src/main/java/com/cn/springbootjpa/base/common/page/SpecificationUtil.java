package com.cn.springbootjpa.base.common.page;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

	private SpecificationUtil() {
		
	}
	
	public static <T> Specification<T> c2s(List<QueryCondition> conditions) {
		Specification<T> result = new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate[] predicates = new Predicate[conditions.size()];
				for (int i = 0; i < conditions.size(); i++) {
					QueryCondition queryCondition = conditions.get(i);
					Specification<T> c2s = queryCondition.c2s();
					predicates[i] = c2s.toPredicate(root, query, cb);
				}
				
				Predicate and = cb.and(predicates);
				
				return and;
			}
		};
		
		return result;
	}
	
	@Deprecated
	public static <T> Specification<T> c2s(QueryCondition condition) {
		Specification<T> c2s = condition.c2s();
		
		return c2s;
	}
	
	@SafeVarargs
	public static <T> Specification<T> and(Specification<T>... specis){
		int length = specis.length;
		Specification<T> result = new Specification<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate[] predicates = new Predicate[length];
				for (int i = 0; i < length; i++) {
					predicates[i] = specis[i].toPredicate(root, query, cb);
				}
				
				return cb.and(predicates);
			}
		};
		
		return result;
	}
	
//	private static <T> Predicate c2p(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, QueryCondition condition) {
//		String field = condition.getField();
//		Path<Object> fieldPath = null;
//		if(!Strings.isNullOrEmpty(field)) {
//			fieldPath = root.get(field);
//		}
//		Object value1 = condition.getValue1();
//		Object value2 = condition.getValue2();
//		switch (condition.getCompareExpression()) {
//		case Eq:
//			return cb.equal(fieldPath, value1);
//		case NotEq:
//			return cb.notEqual(fieldPath, value1);
//		case Between:
//			return betweenPre(cb, condition, fieldPath, value1, value2);
//		case In:
//			return inPre(cb, fieldPath, value1);
//		case Like: 
//			return cb.like(fieldPath.as(String.class), "%" + value1 + "%");
//		case LikeBegin: 
//			return cb.like(fieldPath.as(String.class), value1 + "%");
//		case LikeEnd:
//			return cb.like(fieldPath.as(String.class), "%" + value1);
//		case Le:
//			return cb.le(fieldPath.as(Number.class), (Number) value1);
//		case Lt:
//			return cb.lt(fieldPath.as(Number.class), (Number) value1);
//		case Ge:
//			return cb.ge(fieldPath.as(Number.class), (Number) value1);
//		case Gt:
//			return cb.gt(fieldPath.as(Number.class), (Number) value1);
//		case IsNull:
//			return cb.isNull(fieldPath);
//		case NotNull:
//			return cb.isNotNull(fieldPath);
//		case And:
//			return andPre(root, query, cb, value1, value2);
//		case Or:
//			return orPre(root, query, cb, value1, value2);
//		case Not:
//			return notPre(root, query, cb, value1);
//		default:
//			return null;
//		}
//	}
//
//	private static <T> Predicate notPre(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, Object value1) {
//		Predicate c2p1 = c2p(root, query, cb, (QueryCondition) value1);
//		return cb.not(c2p1);
//	}
//
//	private static <T> Predicate orPre(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, Object value1,
//			Object value2) {
//		Predicate c2p1 = c2p(root, query, cb, (QueryCondition) value1);
//		Predicate c2p2 = c2p(root, query, cb, (QueryCondition) value2);
//		return cb.or(c2p1, c2p2);
//	}
//
//	private static <T> Predicate andPre(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, Object value1,
//			Object value2) {
//		Predicate c2p1 = c2p(root, query, cb, (QueryCondition) value1);
//		Predicate c2p2 = c2p(root, query, cb, (QueryCondition) value2);
//		return cb.and(c2p1, c2p2);
//	}
//
//	private static Predicate inPre(CriteriaBuilder cb, Path<Object> fieldPath, Object value1) {
//		In<Object> in = cb.in(fieldPath);
//		Object[] ite = (Object[]) value1;
//		for (Object object : ite) {
//			in.value(object);
//		}
//		return in;
//	}
//
//	private static Predicate betweenPre(CriteriaBuilder cb, QueryCondition condition, Path<Object> fieldPath, Object value1,
//			Object value2) {
//		Class<?> fieldClazz = condition.getFieldClazz();
//		if(fieldClazz.equals(String.class)) {
//			return cb.between(fieldPath.as(String.class), (String)value1, (String)value2);
//		}
//		if(Integer.class.equals(fieldClazz)) {
//			return cb.between(fieldPath.as(Integer.class), (Integer)value1, (Integer)value2);
//		}
//		if(Double.class.equals(fieldClazz)) {
//			return cb.between(fieldPath.as(Double.class), (Double)value1, (Double)value2);
//		}
//		if(Date.class.equals(fieldClazz)) {
//			return cb.between(fieldPath.as(Date.class), (Date)value1, (Date)value2);
//		}
//		
//		return null;
//	}
}
