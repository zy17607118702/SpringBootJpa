
package com.cn.springbootjpa.base.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhangGuojie
 * 
 */
@Getter
@Setter
public class PageRequest extends HashMap<String, Object> {

	private static final long serialVersionUID = 645612594103872118L;

	/**
	 * Result sort direction is ascending
	 */

	public static final String SORT_DIRECTION_ASC = "ASC";

	/**
	 * Result sort direction is descending
	 */
	public static final String SORT_DIRECTION_DESC = "DESC";

	/* direction */
	private String direction = SORT_DIRECTION_ASC;

	/**
	 * current page
	 */
	private int page = 1;

	/**
	 * max query result line number.
	 */
	private int pageSize = 10;

	/**
	 * Query condition by specific business object.
	 */

	/**
	 * sort condition, every object in list just like the [Property1 ASC] [Property2
	 * ASC]
	 */
	private List<Sort> sorts = new ArrayList<>();

	/**
	 * 添加query condition
	 * 
	 * @param key
	 *            查询条件key
	 * @param value
	 *            查询条件value
	 */
	public void addSearch(String key, String value) {
		this.put(key, value);
	}

	public int getCurrentIndex() {
		return (page - 1) * pageSize;
	}

	/**
	 * reset the current page index reset field reset query condition.
	 */
	public void reset() {
		this.clear();
	}
}