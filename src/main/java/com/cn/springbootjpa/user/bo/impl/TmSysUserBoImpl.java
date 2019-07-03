/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.dao.TmSysUserDao;
import com.cn.springbootjpa.user.entity.TmSysUser;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional

public class TmSysUserBoImpl extends BaseBoImpl<TmSysUser, Integer> implements TmSysUserBo {
	@Autowired
	private TmSysUserDao dao;

	@Override
	public BaseDao<TmSysUser, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public TmSysUser checkPassword(String userName,String password) {
		
		return  dao.checkPassword(userName, password);
	}
}
