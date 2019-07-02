
package com.cn.springbootjpa.util;


/**
 * @author Administrator
 * dao util is utility class, this class keep the hql key workd.
 */
public class DaoUtil {
 
/**
* 构造函数
*/
private DaoUtil(){
 
}

/* add operator */
/**
* add operator
*/
private final static String AND_OP=" and ";
 
/* or operator */
/**
* or operator
*/
private final static String OR_OP=" or ";

/**
* add the condition, and join its.
* @param conditions conditons.
* @return the result joined.
*/
public static String andCondition(String... conditions){
return joinCondition(AND_OP,true,conditions);
}
 
/**
* 用or来连接conditions
* @param conditions 将要用or连接的conditions
* @return 返回连接结果
*/
public static String orCondition(String... conditions){
return joinCondition(OR_OP,true,conditions);
}
 
/**
* 连接各种conditions通过or，and或者是其他的连接符号。
* @param joinOp 连接符号
* @param needBracket 是否要加括号
* @param conditions 要连接的conditions
* @return 返回连接后的结果。
*/
private static String joinCondition(String joinOp,boolean needBracket,String... conditions){
if (conditions.length==0)
return "";
StringBuffer buf=new StringBuffer();
if (needBracket)
buf.append('(');
buf.append(conditions[0]);
for(int i=1;i<conditions.length;i++){
buf.append(joinOp).append(conditions[i]);
}
if (needBracket)
buf.append(')');
return buf.toString();
}
 
/**
* 判断attribute name是否为null
* @param attributeName attribute name。
* @return 返回attribute name is null string。
*/
public static String isNull(String attributeName){
return "("+attributeName+" is null)";
}
 
/**
* 返回 attribute name is not null语句
* @param attributeName attribute name
* @return 返回 attribute name is not null语句
*/
public static String isNotNull(String attributeName){
return "("+attributeName+" is not null)";
}
 
 
/**
* 获取count sql语句。
* @param ql sql语句
* @return 返回sql语句 例如select count(*) 
*/
public static String getCountQL(String ql){
int start=getFirstIndex(ql,"from");
int end=getLastIndex(ql,"order by");
if (end<0)
return "select count(*) "+ql.substring(start);
else
return "select count(*) "+ql.substring(start,end);
}
 
/**
* get first index from key word.
* @param ql sql 
* @param keyword key word.
* @return
*/
private static int getFirstIndex(String ql, String keyword){
int index=ql.indexOf(keyword);
int index2=ql.indexOf(keyword.toUpperCase());
if (index<0||(index2>=0&&index>index2))
index=index2;
return index;
}
 
/**
* get last index from ql.
* @param ql sql
* @param keyword key word.
* @return return the location of the ql.
*/
private static int getLastIndex(String ql,String keyword){
int index=ql.lastIndexOf(keyword);
int index2=ql.lastIndexOf(keyword.toUpperCase());
if (index<0||(index2>=0&&index<index2)){
index=index2;
}
return index;
}

}