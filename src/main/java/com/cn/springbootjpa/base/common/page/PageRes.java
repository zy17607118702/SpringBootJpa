package com.cn.springbootjpa.base.common.page;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRes<T> {
	private List<T> rows;
	private int total; // 总页数
	private int pageIndex; // 第几页
	private int pageSize; // 单页条数
	private Long records; // 总条数

	public static <T> PageRes<T> toRes(Page<T> page) {
		PageRes<T> res = new PageRes<>();
		res.setRows(page.getContent());
		res.setRecords(page.getTotalElements());
		res.setTotal(page.getTotalPages());
		res.setPageIndex(page.getNumber());
		res.setPageSize(page.getSize());

		return res;
	}
}
