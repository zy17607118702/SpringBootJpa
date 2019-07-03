package com.cn.springbootjpa.base.common.page;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRes<T> {
	private List<T> content;
	private int pageTotal; // 总页数
	private int pageIndex; // 第几页
	private int pageSize; // 单页条数
	private Long total; // 总条数

	public static <T> PageRes<T> toRes(Page<T> page) {
		PageRes<T> res = new PageRes<>();
		if(page!=null && page.getSize()!=0) {
			res.setContent(page.getContent());
			res.setTotal(page.getTotalElements());
			res.setPageTotal(page.getTotalPages());
			//修复bug 第一页是0
			res.setPageIndex(page.getNumber()+1);
			res.setPageSize(page.getSize());
		}else {
			res.setContent(null);
			res.setTotal(0l);
			res.setPageTotal(0);
			res.setPageIndex(0);
			res.setPageSize(20);
		}
		return res;
	}
}
