
package com.cn.springbootjpa.base.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class Sort implements Serializable {

	private static final long serialVersionUID = -6574714152350941413L;
	private String prop;
	private String order;
	private boolean ignoreCase;

	public boolean isAcending() {
		return !"descending".equalsIgnoreCase(order);
	}
}