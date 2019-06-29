package com.cn.springbootjpa.base.common.page;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageRes<T> {
	private List<T> rows;
	private int total;      // 总页数
	private int pageIndex; // 第几页
	private int pageSize;
	private Long records;  // 总条数
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	

	public static <T> PageRes<T> toRes(Page<T> page){
		PageRes<T> res = new PageRes<>();
		res.setRows(page.getContent());
		res.setRecords(page.getTotalElements());
		res.setTotal(page.getTotalPages());
		res.setPageIndex(page.getNumber());
		res.setPageSize(page.getSize());
		
		return res;
	}
}
