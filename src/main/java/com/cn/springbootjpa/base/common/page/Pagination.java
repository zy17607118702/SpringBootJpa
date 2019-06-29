package com.cn.springbootjpa.base.common.page;

import lombok.Data;

@Data
public class Pagination {
	private int current;
	private int pageSize;
	private long total;
}
