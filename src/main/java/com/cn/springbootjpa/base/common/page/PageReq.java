package com.cn.springbootjpa.base.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sort; // 排序字段
	private Boolean sord; // 排序顺序 true是正序 false是倒序
	private Integer page;// 页面
	private Integer rows;// 单页条数

	public Sort getPageSort() {
		Direction pageDirection = getPageDirection();
		List<String> sorts = new ArrayList<>();
		if (getSort() == null || StringUtils.isAllBlank(getSort())) {
			sorts.add("id");
		}else {
			sorts.add(getSort());
		}
		return new Sort(pageDirection, sorts);
	}

	public Direction getPageDirection() {
		Direction direction = Direction.DESC;
		if (sord !=null && sord) {
			direction = Direction.ASC;
		}
		return direction;
	}

	@SuppressWarnings("deprecation")
	protected Pageable getPageable() {
		Sort pageSort = getPageSort();
		if (pageSort == null) {
			return new PageRequest(page - 1, rows);
		} else {
			return new PageRequest(page - 1, rows, pageSort);
		}
	}

	@SuppressWarnings("deprecation")
	protected Pageable getPageableStartByZero() {
		Sort pageSort = getPageSort();
		if (pageSort == null) {
			return new PageRequest(page, rows);
		} else {
			return new PageRequest(page, rows, pageSort);
		}
	}

	@SuppressWarnings("deprecation")
	public static Pageable getPageable(PageReq pageReq) {
		if (pageReq == null) {
			return new PageRequest(0, 20);
		} else {
			return pageReq.getPageable();
		}
	}

	@SuppressWarnings("deprecation")
	public static Pageable getPageable(PageReq pageReq, boolean start0) {
		if (pageReq == null) {
			return new PageRequest(0, 20);
		} else {
			if (start0) {
				return pageReq.getPageableStartByZero();
			} else {
				return pageReq.getPageable();
			}
		}
	}

}
