package com.cn.springbootjpa.user.dao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TmSysResource;

@Repository
public interface TmSysResourceDao extends BaseDao<TmSysResource, Integer> {
	
}
