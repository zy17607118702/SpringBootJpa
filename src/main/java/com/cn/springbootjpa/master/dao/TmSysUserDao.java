package com.cn.springbootjpa.master.dao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.master.entity.TmSysUser;

@Repository
public interface TmSysUserDao extends BaseDao<TmSysUser, Integer> {

}
