

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
package com.cn.springbootjpa.base.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yurb Created on Dec 4, 2007
 * 
 */
@Data
public class PageResult<T> implements java.io.Serializable {

private static final long serialVersionUID = 71250954727227317L;

/**
* current recorder index
*/
private int currentIndex = 0;

/**
* total recorder count
*/
private long total = 0;

/**
* 当前页数
*/
private int currentPage = 1;

/**
* 初始化页数为0
*/
@Deprecated
private int countPage = 0;

private int pageSize;
/**
* content
*/
private List<T> content = new ArrayList<>();
 
public PageResult(List<T> dataList, long totalCount){
 
this.content = dataList;
this.total = totalCount;
 
}

public PageResult() {
}
}
