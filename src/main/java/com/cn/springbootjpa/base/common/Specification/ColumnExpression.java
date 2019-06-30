/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

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

public class ColumnExpression implements Criterion {
 
    private final String fieldNameA;
 
    private final String fieldNameB;
 
    private Operator operator;      //计算符
 
 
    ColumnExpression(String fieldNameA, String fieldNameB, Operator operator) {
        this.fieldNameA = fieldNameA;
        this.fieldNameB = fieldNameB;
        this.operator = operator;
    }
 
 
 
    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expressionA ;
        if(fieldNameA.contains(".")){
            String[] names = StringUtils.split(fieldNameA, ".");
            expressionA = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expressionA = expressionA.get(names[i]);
            }
        }else{
            expressionA = root.get(fieldNameA);
        }
 
        Path expressionB ;
        if(fieldNameB.contains(".")){
            String[] names = StringUtils.split(fieldNameB, ".");
            expressionB = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expressionB = expressionB.get(names[i]);
            }
        }else{
            expressionB = root.get(fieldNameB);
        }
        switch (operator) {
            case EQ:
                return builder.equal(expressionA, expressionB);
            case NE:
                return builder.notEqual(expressionA, expressionB);
            default:
                return null;
        }
    }
}