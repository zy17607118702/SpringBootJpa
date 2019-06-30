/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangyang
 *
 */

public class BetweenExpression implements Criterion {
 
    private final String fieldName;
    private Object lo;
    private Object hi;
 
 
    BetweenExpression(String fieldName, Object lo, Object hi) {
        this.fieldName = fieldName;
        this.lo = lo;
        this.hi = hi;
 
    }
 
 
    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
 
 
        if (lo instanceof Date && hi instanceof Date) {
            return builder.between(expression, (Date)lo, (Date)hi);
        } else if (lo instanceof String && hi instanceof String) {
            return builder.between(expression, (String)lo, (String)hi);
        } else if (lo instanceof Integer && hi instanceof Integer) {
            return builder.between(expression, (Integer)lo, (Integer)hi);
        } else if (lo instanceof Double && hi instanceof Double) {
            return builder.between(expression, (Double)lo, (Double)hi);
        } else if (lo instanceof BigDecimal && hi instanceof BigDecimal) {
            return builder.between(expression, (BigDecimal)lo, (BigDecimal)hi);
        } else {
            return null;
        }
    }
}