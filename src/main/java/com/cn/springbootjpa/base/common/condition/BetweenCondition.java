package com.cn.springbootjpa.base.common.condition;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cn.springbootjpa.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询两个时间时间差表达式
 * @author zhangyang
 *
 * @param <T>
 */
@Getter
@Setter
public class BetweenCondition<T extends Comparable<? super T>> implements ICondition {

	private String f;
	private T val1;
	private T val2;
	
	public BetweenCondition() {
		
	}

	public BetweenCondition(String field, T v1, T v2) {
		this.f = field;
		val1 = v1;
		val2 = v2;
	}
	
	@Override
	public String toStrCondition() {
		if (val1 instanceof Date) {
			String dataStr1 = DateUtil.format((Date)val1);
			String dataStr2 = DateUtil.format((Date) val2);
			return f + " BETWEEN '" + dataStr1 + "' AND '" + dataStr2 + "'";
		} else if(val1 instanceof Number) {
			return f + " BETWEEN " + val1 + " AND " + val2 + "";
		}else{
			return f + " BETWEEN '" + val1 + "' AND '" + val2 + "'";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Specification<T> c2s() {
		Specification<T> result = new Specification<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<T> fieldPath = root.get(f);
				if(val1 instanceof Date) {
					return cb.between(fieldPath.as(Date.class), (Date) val1, (Date) val2);
				}
				if(val1 instanceof Number) {
					return cb.between(fieldPath, val1, val2);
				}
				if(val1 instanceof String) {
					return cb.between(fieldPath.as(String.class), (String)val1, (String)val2);
				}
				
				return null;
			}

		};
		
		return result;
	}
	

	@Override
	public ICondition copy() {
		BetweenCondition<T> betweenCondition = new BetweenCondition<>(f, val1, val2);
		
		return betweenCondition;
	}

}
