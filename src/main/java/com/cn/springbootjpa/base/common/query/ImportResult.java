/**********************************************************************
 * FILE : PageResult.java CREATE DATE : 2008-12-10 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2008-12-10 | ZhangGuojie | 创建草稿版本
 * ---------------------------------------------------------------------
 * Licensed Materials - Property of IBM
 * 
 * 6949-31G
 * 
 * (C) Copyright IBM Corp. 2007 All Rights Reserved. (C) Copyright State of New
 * York 2002 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 *
 ******************************************************************************/
package com.cn.springbootjpa.base.common.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yurb Created on Dec 4, 2007
 * 
 */
@Getter
@Setter
public class ImportResult<T> implements java.io.Serializable {

	private static final long serialVersionUID = 71250954727227317L;

	/**
	 *   失败数量
	 */
	private long failTotal = 0;

	/**
	 *  总数量
	 */
	private long total = 0;

	/**
	 *   成功数量
	 */
	private long succeedTotal = 1;

	/**
	 * content 失败数据
	 */
	private List<T> content = new ArrayList<>();
	
	public ImportResult(List<T> dataList, long total,long succeedTotal,long failTotal){	
		this.content = dataList;
		this.total = total;	
		this.succeedTotal = succeedTotal;
		this.failTotal = failTotal;

	}

	public ImportResult() {
	}
}
