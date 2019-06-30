/**
 * 
 */
package com.cn.springbootjpa.base.common.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.cn.springbootjpa.util.StringUtil;

/**
 * @author zhangyang
 *
 */
public class Restrictions {
    /**
     * 等于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression eq(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.EQ);
    }
 
    /**
     * 不等于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression ne(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.NE);
    }
 
    /**
     * 模糊匹配
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression like(String fieldName, String value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LIKE);
    }
 
    /**
     * 模糊匹配
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression leftLike(String fieldName, String value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LEFTLIKE);
    }
 
    /**
     * 模糊匹配
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression rightLike(String fieldName, String value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.RIGHTLIKE);
    }
 
 
 
    /**
     * 大于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression gt(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GT);
    }
 
    /**
     * 小于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression lt(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LT);
    }
 
    /**
     * 大于等于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression gte(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GTE);
    }
 
    /**
     * 小于等于
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression lte(String fieldName, Object value) {
        if(value == null) {
            return null;
        }
 
        return new SimpleExpression (fieldName, value, Criterion.Operator.LTE);
    }
 
    /**
     * 并且
     * @param criterions
     * @return
     */
    public static LogicalExpression and(Criterion... criterions){
        return new LogicalExpression(criterions, Criterion.Operator.AND);
    }
    /**
     * 或者
     * @param criterions
     * @return
     */
    public static LogicalExpression or(Criterion... criterions){
        return new LogicalExpression(criterions, Criterion.Operator.OR);
    }
 
 
    /**
     * 包含于
     * @param fieldName
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static SimpleExpression in(String fieldName, Collection value) {
        return new SimpleExpression(fieldName, Criterion.Operator.IN, value.toArray());
    }
 
 
    /**
     * 不包含于
     * @param fieldName
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression notIn(String fieldName, Collection value) {
        if((value==null||value.isEmpty())){
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i=0;
        for(Object obj : value){
            ses[i]=new SimpleExpression(fieldName,obj, Criterion.Operator.NE);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.AND);
    }
 
    /**
     * 列为空
     * @param fieldName
     * @return
     */
    public static LogicalExpression isNull(String fieldName) {
        SimpleExpression[] ses = new SimpleExpression[1];
        ses[0] = new SimpleExpression(fieldName, Criterion.Operator.ISNULL);
        return new LogicalExpression(ses, Criterion.Operator.ISNULL);
    }
 
    /**
     * 列不为空
     * @param fieldName
     * @return
     */
    public static LogicalExpression isNotNull(String fieldName) {
        SimpleExpression[] ses = new SimpleExpression[1];
        ses[0] = new SimpleExpression(fieldName, Criterion.Operator.ISNOTNULL);
        return new LogicalExpression(ses, Criterion.Operator.ISNOTNULL);
    }
 
 
 
    /**
     * 时间范围
     * @param fieldName
     * @return
     */
    public static LogicalExpression between(String fieldName, Object startDate, Object endDate) {
        BetweenExpression[] bes = new BetweenExpression[1];
        bes[0] = new BetweenExpression(fieldName, startDate, endDate);
        return new LogicalExpression(bes, Criterion.Operator.BETWEEN);
    }
 
    /**
     * 等于
     * @param fieldNameA
     * @param fieldNameB
     * @return
     */
    public static ColumnExpression eqCol(String fieldNameA, String fieldNameB) {
        if(StringUtil.isNullOrBlank(fieldNameA) || StringUtil.isNullOrBlank(fieldNameB)) {
            return null;
        }
        return new ColumnExpression(fieldNameA, fieldNameB, Criterion.Operator.EQ);
    }
 
    /**
     * bu等于
     * @param fieldNameA
     * @param fieldNameB
     * @return
     */
    public static ColumnExpression neCol(String fieldNameA, String fieldNameB) {
        if(StringUtil.isNullOrBlank(fieldNameA) || StringUtil.isNullOrBlank(fieldNameB)) {
            return null;
        }
        return new ColumnExpression(fieldNameA, fieldNameB, Criterion.Operator.NE);
    }
 
 
 
    /**
     * 时间范围
     * @param fieldName
     * @return
     */
    public static LogicalExpression eqDate(String fieldName, Object date) {
        BetweenExpression[] bes = new BetweenExpression[1];
 
        Date startDate;
        Date endDate;
        try {
            if(date instanceof String){
                startDate = stringToDateTime(date.toString() + " 00:00:00");
                endDate = stringToDateTime(date.toString() + " 23:59:59");
            }else if(date instanceof Date){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date)date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND,0);
 
                startDate = calendar.getTime();
 
                calendar.set(Calendar.HOUR_OF_DAY,23);
                calendar.set(Calendar.MINUTE,59);
                calendar.set(Calendar.SECOND,59);
 
                endDate = calendar.getTime();
            }else{
                return null;
            }
        }catch (Exception ignored){
            return null;
        }
 
        bes[0] = new BetweenExpression(fieldName, startDate, endDate);
        return new LogicalExpression(bes, Criterion.Operator.BETWEEN);
    }
 
 
    private final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
 
    private static Date stringToDateTime(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
 
            DateFormat df = new SimpleDateFormat(DATETIME_PATTERN);
            df.setLenient(false);
            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}