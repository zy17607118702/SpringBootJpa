package com.cn.springbootjpa.base.common.page;

import java.util.List;

public interface QueryObj {
    List<QueryCondition> toConditions();

}