/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangyang
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class SimpleExpression implements Criterion {
    private String fieldName;       //属性名
    private Object value;           //对应值
    private Object[] values;           //对应值
    private Operator operator;      //计算符
 
    protected SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }
 
 
    protected SimpleExpression(String fieldName, Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }
 
    protected SimpleExpression(String fieldName, Operator operator, Object... values) {
        this.fieldName = fieldName;
        this.values = values;
        this.operator = operator;
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        Path expression ;
        if(fieldName.contains(".")){
            String[] names = StringUtils.split(fieldName, ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        }else{
            expression = root.get(fieldName);
        }
 
        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like((Expression<String>) expression, "%" + value + "%");
            case LEFTLIKE:
                return builder.like((Expression<String>) expression, "%" + value);
            case RIGHTLIKE:
                return builder.like((Expression<String>) expression, value + "%");
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case ISNULL:
                return builder.isNull(expression);
            case ISNOTNULL:
                return builder.isNotNull(expression);
            case IN:
                return ((CriteriaBuilderImpl)builder).in(expression, values);
            default:
                return null;
        }
    }
}