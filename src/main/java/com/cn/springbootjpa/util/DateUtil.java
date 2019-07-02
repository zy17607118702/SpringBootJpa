
package com.cn.springbootjpa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private DateUtil() {
		
	}
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DEFAULT_DATE_PATTERN);
		return formatTool.format(date);
	}
	
	/**
	 * 获取今日0点
	 * @return
	 */
	public static Date getTodayBegin() {
        return getBeginOfDay(new Date());
	}
	
	public static Date getMonthBegin() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public static Date before(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1 * i);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取某天的0点
	 * @param day
	 * @return
	 */
	public static Date getBeginOfDay(Date day) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        
        return zero;
	}
	
	public static Date getEndOfDay(Date day) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date zero = calendar.getTime();
        
        return zero;
	}

	public static Date parseDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
}
