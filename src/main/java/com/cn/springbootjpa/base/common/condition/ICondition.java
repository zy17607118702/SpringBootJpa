package com.cn.springbootjpa.base.common.condition;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.cn.springbootjpa.base.util.DateUtil;


public interface ICondition {
	String toStrCondition();    // 转成字符串，进行拼接
	<T> Specification<T> c2s();  // 转criteria查询
	ICondition copy();  // 复制一份
	
	public default String getValueStr(Object value) {
		if(value instanceof Number) {
			return "" + value + "";
		}
		if(value instanceof Date) {
			return "'" + DateUtil.format((Date) value) + "'";
		}
		
		return "'" + value + "'";
	}
}
