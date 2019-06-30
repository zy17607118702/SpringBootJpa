/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author zhangyang
 *
 */
public interface Criterion {
	/**
	 * 操作符
	 */
	enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, ISNULL, ISNOTNULL, LEFTLIKE, RIGHTLIKE, BETWEEN, IN
	}

	Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}