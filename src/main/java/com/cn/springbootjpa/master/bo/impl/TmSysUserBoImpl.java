/**
 * 
 */
package com.cn.springbootjpa.master.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.master.bo.TmSysUserBo;
import com.cn.springbootjpa.master.dao.TmSysUserDao;
import com.cn.springbootjpa.master.entity.TmSysUser;

/**
 * @author zhangyang
 *
 */
@Service
public class TmSysUserBoImpl extends BaseBoImpl<TmSysUser, Integer> implements TmSysUserBo {
	@Autowired
	private TmSysUserDao dao;

	@Override
	public BaseDao<TmSysUser, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
