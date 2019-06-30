package com.find.util.web.page;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.util.DateUtil;

public class QueryConditionTest {
	@Test
	public void testGt() throws ParseException {
		QueryCondition gt = QueryCondition.gt("a", 1);
		Assert.assertEquals("a>1", gt.toString());
		System.out.println(gt.c2s());
		QueryCondition gt2 = QueryCondition.gt("a", "1");
		Assert.assertEquals("a>'1'", gt2.toString());
//		QueryCondition gt3 = QueryCondition.gt("a", DateUtil.parseDate("2015", "yyyy"));
//		Assert.assertEquals("a>'2015-01-01 00:00:00'", gt3.toString());
		
	}
}
