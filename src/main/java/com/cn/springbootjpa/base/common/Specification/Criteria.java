/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author zhangyang
 *
 */
public class Criteria<T> implements Specification<T> {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Criterion> criterions = new ArrayList<Criterion>();
 
 
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for(Criterion c : criterions){
                predicates.add(c.toPredicate(root, query, builder));
            }
            // 将所有条件用 and 联合起来
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }
 
    /**
     * 增加简单条件表达式
     * @param criterion
     */
    public void add(Criterion criterion){
        if(criterion!=null){
            criterions.add(criterion);
        }
    }
 
 
 
    private boolean isField(Field[] fields, String queryKey) {
        if (fields == null || fields.length == 0) {
            return false;
        }
        for (Field field : fields) {
            if (field.getName().equals(queryKey)) {
                return true;
            }
        }
        return false;
    }
}