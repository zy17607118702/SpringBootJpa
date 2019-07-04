package com.cn.springbootjpa.user.dao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TmSysRole;

@Repository
public interface TmSysRoleDao extends BaseDao<TmSysRole, Integer> {
	
}
