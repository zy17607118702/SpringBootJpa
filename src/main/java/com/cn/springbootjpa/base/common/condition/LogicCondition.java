package com.cn.springbootjpa.base.common.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 逻辑条件表达式 用于复杂条件时使用，如但属性多对应值的OR查询等 
 * @author zhangyang
 *
 */

public class LogicCondition implements ICondition {

	private List<ICondition> cons;
	private CompareExpression expression; // 只会包含and， or， not

	public static ICondition and(ICondition condition1, ICondition condition2, ICondition... conditions) {
		List<ICondition> list = new ArrayList<>();
		list.add(condition1);
		list.add(condition2);
		for (int i = 0; i < conditions.length; i++) {
			list.add(conditions[i]);
		}

		LogicCondition condition = new LogicCondition();
		condition.setCons(list);
		condition.setExpression(CompareExpression.And);

		return condition;
	}

	public static ICondition or(ICondition condition1, ICondition condition2, ICondition... conditions) {
		List<ICondition> list = new ArrayList<>();
		list.add(condition1);
		list.add(condition2);
		for (int i = 0; i < conditions.length; i++) {
			list.add(conditions[i]);
		}

		LogicCondition condition = new LogicCondition();
		condition.setCons(list);
		condition.setExpression(CompareExpression.Or);

		return condition;
	}

	public static ICondition not(ICondition condition) {
		List<ICondition> list = new ArrayList<>();
		list.add(condition);

		LogicCondition result = new LogicCondition();
		result.setCons(list);
		result.setExpression(CompareExpression.Not);

		return result;
	}

	@Override
	public ICondition copy() {
		LogicCondition result = new LogicCondition();
		result.setCons(cons);
		result.setExpression(expression);

		return result;
	}

	@Override
	public String toStrCondition() {
		if (expression.equals(CompareExpression.Not)) {
			return "(NOT (" + cons.get(0).toStrCondition() + "))";
		} else {
			String joinChar = " " + expression.toString() + " ";
			String collect = cons.stream().map(item -> item.toStrCondition())
					.collect(Collectors.joining(joinChar, "(", ")"));

			return collect;
		}
	}

	@Override
	public Specification<Object> c2s() {
		Specification<Object> result = new Specification<Object>() {

			@Override
			public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate[] restrictions = new Predicate[cons.size()];
				for (int i = 0; i < cons.size(); i++) {
					restrictions[i] = cons.get(i).c2s().toPredicate(root, query, cb);
				}
				switch (expression) {
				case And:
					Predicate and = cb.and(restrictions);

					return and;
				case Or:
					Predicate or = cb.or(restrictions);

					return or;
				case Not:
					Predicate not = cb.not(restrictions[0]);

					return not;

				default:
					break;
				}
				return null;
			}

		};

		return result;
	}

	public List<ICondition> getCons() {
		return cons;
	}

	public void setCons(List<ICondition> cons) {
		this.cons = cons;
	}

	public CompareExpression getExpression() {
		return expression;
	}

	public void setExpression(CompareExpression expression) {
		this.expression = expression;
	}
}
