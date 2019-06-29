package com.cn.springbootjpa.base.bo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.domain.ExampleMatcher.StringMatcher;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringMatcherType {
	/**
	 * 匹配方式
	 * @return
	 */
	StringMatcher value();
}
