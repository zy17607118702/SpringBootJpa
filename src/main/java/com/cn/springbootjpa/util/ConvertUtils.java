package com.cn.springbootjpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to convert one object to another.
 */
@Slf4j
public final class ConvertUtils {

	/**
	 * Converts an array of string to comma separator string.
	 * 
	 * @param c
	 *            an array of string.
	 * @return comman separator string.
	 */
	public static String convertArrayToCommaString(String[] c) {
		return convertListToCommaString(Arrays.asList(c));
	}

	/**
	 * Method to convert a ResourceBundle to a Map object.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Map a populated map
	 */
	public static Map<String, String> convertBundleToMap(ResourceBundle rb) {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}
		return map;
	}

	/**
	 * Method to convert a ResourceBundle to a Properties object.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Properties a populated properties object
	 */
	public static Properties convertBundleToProperties(ResourceBundle rb) {
		Properties props = new Properties();

		for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
			String key = keys.nextElement();
			props.put(key, rb.getString(key));
		}

		return props;
	}

	/**
	 * Parse comma separated string value to Long value then return result as Long
	 * array.
	 * 
	 * @param commaString
	 * @return
	 */
	public static final Long[] convertCommaToLongArray(String commaString) {
		if (commaString == null) {
			return null;
		}
		if (!"".equals(commaString.trim())) {
			return convertStringArrayToLongArray(commaString.split(","));
		}
		return null;
	}

	public static final String[] convertCommaToStringArray(String commaString) {
		if (commaString != null && !"".equals(commaString.trim())) {
			return trim(commaString.split(","));
		}
		return null;
	}

	/**
	 * Converts a collection of object to comma separator string.
	 * 
	 * @param c
	 *            a collection of string.
	 * @return comman separator string.
	 */
	public static String convertListToCommaString(Collection<?> c) {
		String cs = "";
		if (c != null && !c.isEmpty()) {
			for (Object s : c) {
				cs += "," + s;
			}
			if (!StringUtils.isBlank(cs) && cs.startsWith(",")) {
				cs = cs.substring(1);
			}
		}
		return cs;
	}

	/**
	 * 将对象字符串值转换成Boolean值，能转换为TRUE值包括(不区分大小写)："true","yes","1"
	 * 
	 * @param v
	 *            object
	 * @return
	 */
	public static final Boolean convertObjectToBoolean(Object v) {
		if (v != null && !StringUtils.isBlank(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
			try {
				Boolean b = Boolean.FALSE;
				if (v != null) {
					String vs = v.toString();
					if ("true".equalsIgnoreCase(vs) || "1".equalsIgnoreCase(vs) || "yes".equalsIgnoreCase(vs)
							|| "on".equalsIgnoreCase(vs)) {
						b = Boolean.TRUE;
					}
				}

				return b;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Converts an object string value to Double value.
	 * 
	 * @param v
	 *            Object value.
	 * @return Double value of object, null if object can not be converted to
	 *         Double.
	 */
	public static final Double convertObjectToDouble(Object v) {
		if (v != null && !StringUtils.isBlank(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
			try {
				String vs = v.toString();
				Double l = Double.valueOf(vs.replaceAll(",", ""));
				return l;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Converts an object string value to Float value.
	 * 
	 * @param v
	 *            Object value.
	 * @return Float value of object, null if object can not be converted to Float.
	 */
	public static final Float convertObjectToFloat(Object v) {
		if (v != null && !StringUtils.isBlank(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
			try {
				String vs = v.toString();
				Float l = Float.valueOf(vs.replaceAll(",", ""));
				return l;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Converts an object string value to Integer value.
	 * 
	 * @param v
	 *            Object value.
	 * @return Integer value of object, null if object can not be converted to
	 *         Integer.
	 */
	public static final Integer convertObjectToInteger(Object v) {
		if (v != null && !StringUtils.isBlank(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
			try {
				String vs = v.toString();
				Integer l = Integer.valueOf(vs.replaceAll(",", ""));
				return l;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Converts an object string value to Long value.
	 * 
	 * @param v
	 *            Object value.
	 * @return Long value of object, null if object can not be converted to Long.
	 */
	public static final Long convertObjectToLong(Object v) {
		if (v != null && !StringUtils.isBlank(v.toString()) && !"null".equalsIgnoreCase(v.toString())) {
			try {
				String vs = v.toString();
				int idx = vs.indexOf(':');
				if (idx >= 0) {
					vs = vs.substring(idx + 1);
				}
				vs = vs.replaceAll(",|:", "");
				Long l = Long.valueOf(vs.trim());
				return l;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Converts an array of string values to an array of object according to value
	 * prefix.
	 * <ul>
	 * <li>Long: 传递Long类型，如&amp;params=Long:3</li>
	 * <li>Integer: 传递Integer类型，如&amp;params=Integer:3</li>
	 * <li>Double: 传递Double类型，如&amp;params=Double:3</li>
	 * <li>Boolean: 传递Boolean类型，如&amp;params=Boolean:true</li>
	 * <li>Long[]: 传递Long数组类型，如&amp;params=Long[]:true</li>
	 * </ul>
	 * 
	 * @param conditionValues
	 *            an array of string value.
	 * @return
	 */
	public static Object[] convertParams(Object[] conditionValues) {
		if (conditionValues == null || conditionValues.length == 0) {
			return null;
		}
		Object[] values = new Object[conditionValues.length];
		for (int i = 0; i < conditionValues.length; i++) {
			if (conditionValues[i] == null) {
				continue;
			}
			String v = conditionValues[i].toString();
			if (!StringUtils.isBlank(v)) {
				String vl = v.toLowerCase();
				if (vl.startsWith("long:")) {
					values[i] = ConvertUtils.convertObjectToLong(v.substring("long:".length()));
				} else if (vl.startsWith("integer:")) {
					values[i] = ConvertUtils.convertObjectToInteger(v.substring("integer:".length()));
				} else if (vl.startsWith("double:")) {
					values[i] = ConvertUtils.convertObjectToDouble(v.substring("double:".length()));
				} else if (vl.startsWith("boolean:")) {
					values[i] = ConvertUtils.convertObjectToBoolean(v.substring("boolean:".length()));
				} else if (vl.startsWith("long[]:")) {
					values[i] = ConvertUtils.convertCommaToLongArray(v.substring("long[]:".length()));
				} else if (conditionValues[i] instanceof CharSequence || conditionValues[i] instanceof Character) {
					values[i] = v;
				} else {
					values[i] = conditionValues[i];
				}
			}
		}
		return values;
	}

	/**
	 * Parse every element in string array to Long value then return the result as
	 * Long array.
	 * 
	 * @param sArray
	 *            String array.
	 * @return Long array.
	 */
	public static final Long[] convertStringArrayToLongArray(String[] sArray) {
		if (sArray == null || sArray.length == 0) {
			return null;
		}

		List<Long> larray = new ArrayList<Long>();
		for (int i = 0; i < sArray.length; i++) {
			if (StringUtils.isBlank(sArray[i])) {
				continue;
			}
			larray.add(convertObjectToLong(sArray[i].trim()));
		}
		return larray.toArray(new Long[] {});
	}

	/**
	 * Convert an object(may be a comma separated string, an array of string , an
	 * array of Long) to an array of Long.
	 * 
	 * @param o
	 *            object unknow type.
	 * @return
	 */
	public static Long[] convertToLongArray(Object o) {
		if (o instanceof String) {
			return ConvertUtils.convertCommaToLongArray((String) o);
		} else if (o instanceof String[]) {
			return ConvertUtils.convertStringArrayToLongArray((String[]) o);
		} else if (o instanceof Long[]) {
			return (Long[]) o;
		} else if (o instanceof Integer[]) {
			return ConvertUtils.convertIntegerArrayToLongArray((Integer[]) o);
		}
		return null;
	}

	/**
	 * 将Integer数组转成Long数组
	 * 
	 * @param sArray
	 * @return
	 */
	private static Long[] convertIntegerArrayToLongArray(Integer[] sArray) {
		if (sArray == null || sArray.length == 0) {
			return null;
		}

		List<Long> larray = new ArrayList<Long>();
		for (int i = 0; i < sArray.length; i++) {
			if (sArray[i] == null) {
				continue;
			}
			larray.add(convertObjectToLong(sArray[i]));
		}
		return larray.toArray(new Long[] {});

	}

	public static String[] convertToStringArray(Object o) {
		if (o instanceof String) {
			return ConvertUtils.convertCommaToStringArray((String) o);
		} else if (o instanceof String[]) {
			return (String[]) o;
		} else if (o instanceof Object[]) {
			Object[] os = (Object[]) o;
			String[] ss = new String[os.length];
			for (int i = 0; i < os.length; i++) {
				ss[i] = os[i] == null ? null : os[i].toString();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(convertObjectToLong(" "));
	}

	/**
	 * Map转Object
	 * 
	 * @param map
	 *            Map
	 * @param beanClass
	 *            实体类型
	 * @return Object 对象
	 * @throws Exception
	 *             异常
	 * @author Xylon at 2017-11-29
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}

			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	/**
	 * 实体对象转Map
	 * 
	 * @param obj
	 *            对象
	 * @return Map<String, Object> Map
	 * @throws Exception
	 *             异常
	 * @author Xylon at 2017-11-29
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static Map<String, Object> objectToMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			Object o = field.get(obj);
			Object val = "";
			if (o != null && o instanceof java.lang.Long) {
				val = String.valueOf(o);
			} else if (o != null && o instanceof java.lang.Double) {
				val = String.valueOf(o);
			} else if (o != null && o instanceof java.util.Date) {
				val = DateUtils.format((Date) o, DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM);
			} else {
				val = o;
			}
			map.put(field.getName(), val);
		}

		return map;
	}

	/**
	 * Convenience method used by tests to populate an object from a ResourceBundle
	 * 
	 * @param obj
	 *            an initialized object
	 * @param rb
	 *            a resource bundle
	 * @return a populated object
	 */
	public static Object populateObject(Object obj, ResourceBundle rb) {
		try {
			Map<String, String> map = convertBundleToMap(rb);
			BeanUtils.copyProperties(obj, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred populating object: " + e.getLocalizedMessage());
		}

		return obj;
	}

	/**
	 * Trims all element in given array.
	 * 
	 * @param s
	 *            an array of original string.
	 * @return
	 */
	public static String[] trim(String[] s) {
		if (s == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String v : s) {
			list.add(v == null ? null : v.trim());
		}
		return list.toArray(new String[0]);
	}

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private ConvertUtils() {
	}
}