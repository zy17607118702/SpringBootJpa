
/**********************************************************************
* FILE : StringUtil.java
* CREATE DATE : 2008-12-10
* DESCRIPTION :
*
*      
* CHANGE HISTORY LOG
*---------------------------------------------------------------------
* NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
*---------------------------------------------------------------------
* 1  | 2008-06-06 |  ZhangGuojie  |    创建草稿版本
*---------------------------------------------------------------------              
*
******************************************************************************/
package com.cn.springbootjpa.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * @author Administrator this class define the string util.
 */
public final class StringUtil {

	/**
	 * get object string.
	 * 
	 * @param obj
	 *            object.
	 * @return string
	 */
	public static String getObjString(Object obj) {
		if (null == obj)
			return "";
		else
			return obj.toString();
	}

	/**
	 * avoid null, and return value trim.
	 * 
	 * @param value
	 *            string value.
	 * @return the trim of value.
	 */
	public static String avoidNull(String value) {
		return (value == null) ? "" : value.trim();
	}

	/**
	 * 判断string是否为null 或者是blank。
	 * 
	 * @param value
	 *            string value
	 * @return value
	 */
	public static boolean isNullOrBlank(String value) {
		return value == null || "".equals(value.trim());
	}

	/**
	 * 判断String不为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotNullOrBlank(String value) {
		return value != null && !"".equals(value.trim());
	}

	/*
	 * 获取指定UTF-8模式字节长度的字符串
	 */
	/**
	 * 获取指定UTF-8模式字节长度的字符串
	 * 
	 * @param strValue
	 *            输入字符串
	 * @param bytelen
	 *            限制的字节长度
	 * @return 在指定字节长度内的UTF-8转码
	 */
	public static String limitLength(String strValue, int bytelen) {

		// 中文汉字占用三个字节
		int strlen = bytelen / 3;
		int real_bytelen = strlen * 3;

		// 如果为NULL或者空，则直接返回
		if (null == strValue || "".equalsIgnoreCase(strValue)) {
			return "";
		}

		try {
			byte[] utf8_bytes = strValue.getBytes("UTF-8");
			if (utf8_bytes.length <= bytelen)
				return strValue;

			byte[] cutoff_bytes = new byte[real_bytelen];
			System.arraycopy(utf8_bytes, 0, cutoff_bytes, 0, real_bytelen);

			String strResult = new String(cutoff_bytes, "UTF-8");

			return strResult;

		} catch (Exception e) {
			if (strValue.length() < strlen)
				return strValue;
			return strValue.substring(0, strlen);
		}

	}

	/**
	 * 对url进行编码。
	 * 
	 * @param ori_url
	 *            要编码的url
	 * @return 返回url
	 */
	public static String urlEncode(String ori_url) {
		try {
			String tempstr = URLEncoder.encode(ori_url, "UTF-8");
			return tempstr.replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ori_url;
		}
	}

	/**
	 * 编码url
	 * 
	 * @param ori_url
	 *            原URL
	 * @return 编码后URL
	 */
	public static String urlDecode(String ori_url) {
		try {

			String tempstr = URLDecoder.decode(ori_url.replaceAll("%20", "\\+"), "UTF-8");
			return tempstr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ori_url;
		}
	}

	/**
	 * 堆栈异常信息
	 * 
	 * @param e
	 *            异常信息
	 * @return 返回string
	 */
	public static String exceptionStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String returnStr = "";
		if (null != e) {
			e.printStackTrace(pw);
			pw.flush();
			pw.close();
			returnStr = sw.toString();
		}
		return returnStr;
	}

	/**
	 * 往指定字符串的左侧填补指定的字符直到指定最后字符串长度达到指定长度。
	 * 
	 * @param orgString
	 *            源字符串
	 * @param paddingChar
	 *            填充字符
	 * @param len
	 *            最后字符长度
	 * @return 填充后字符串，源字符串为空时，填充所有。
	 */
	public static String paddingLeft(String orgString, char paddingChar, int len) {
		if (isNullOrBlank(orgString)) {
			orgString = "";
		}
		int orgLength = orgString.length();
		for (int i = 0; i < (len - orgLength); i++) {
			orgString = paddingChar + orgString;
		}
		return orgString;
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.paddingLeft("004", '0', 5));
	}

	/**
	 * @Description: 将list转成字符串
	 * @param list：给定的集合
	 * @param separator：分隔符
	 * @return String：结果字符串
	 * @author wwn
	 * @date 2017-11-30
	 */
	@SuppressWarnings("rawtypes")
	public static String join(List list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i < list.size() - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串拼接的时间转换成Date
	 * 
	 * @param dateStr
	 *            日期字符串(例如:2016-10-24)
	 * @param timeStr
	 *            时间字符串(例如:112200)
	 * @return java.util.Date
	 * @throws ParseException
	 */
	public static Date format(String dateStr, String timeStr) throws ParseException {
		if (StringUtils.isEmpty(timeStr) || StringUtils.isEmpty(dateStr)) {
			throw new NullPointerException("dateStr And dateStr Can't be null !");
		}
		if (dateStr.length() < 6) {
			throw new IllegalArgumentException(timeStr);
		}
		String hour = timeStr.substring(0, 2);
		String minute = timeStr.substring(2, 4);
		String second = timeStr.substring(4, 6);
		String time = dateStr + " " + hour + ":" + minute + ":" + second;
		return DateUtils.parse(time, DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
	}
}