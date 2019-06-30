/**
 * 
 */
package com.cn.springbootjpa.base.common;

import java.io.Serializable;
import java.util.HashMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 查询参数HashMap 封装前端查询参数
 * @author zhangyang
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryParam extends HashMap<String, Object> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sort; // 排序字段
	private Boolean sord; // 排序顺序 true是正序 false是倒序
	private Integer page;// 页面
	private Integer rows;// 单页条数

	public void addSearch(String key, String value) {
		this.put(key, value);
	}

	public int getCurrentIndex() {
		return (page - 1) * rows;
	}

	public void reset() {
		this.clear();
	}
}
