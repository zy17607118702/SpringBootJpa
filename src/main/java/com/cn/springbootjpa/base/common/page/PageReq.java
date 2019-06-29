package com.cn.springbootjpa.base.common.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageReq {
	private String sort;    // 排序字段
	private String sord;   // 排序顺序
	private Integer page = 1;
	private Integer rows = 10;
	
	public Sort getPageSort() {
		if(getSort() == null) {
			return null;
		}
		
		Direction pageDirection = getPageDirection();
		List<String> sorts = new ArrayList<>();
		sorts.add(getSort());
		
		return new Sort(pageDirection, sorts);
	}
	
	public Direction getPageDirection() {
		Direction direction = Direction.DESC;
		if("asc".equals(sord) || "ascend".equals(sord)) {
			direction = Direction.ASC;
		}
		
		return direction;
	}
	
	protected Pageable getPageable() {
		Sort pageSort = getPageSort();
		if(pageSort == null) {
			return new PageRequest(page -1, rows);
		} else {
			return new PageRequest(page -1, rows, pageSort);
		}
	}

	protected Pageable getPageableStartByZero() {
		Sort pageSort = getPageSort();
		if(pageSort == null) {
			return new PageRequest(page, rows);
		} else {
			return new PageRequest(page, rows, pageSort);
		}
	}
	
	public static Pageable getPageable(PageReq pageReq) {
		if(pageReq == null) {
			return new PageRequest(0, 20);
		} else {
			return pageReq.getPageable();
		}
	}

	public static Pageable getPageable(PageReq pageReq, boolean start0) {
		if(pageReq == null) {
			return new PageRequest(0, 20);
		} else {
			if(start0){
				return pageReq.getPageableStartByZero();
			} else{
				return pageReq.getPageable();
			}
		}
	}
	
	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
