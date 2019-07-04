/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.bo.TmSysRoleBo;
import com.cn.springbootjpa.user.dao.TmSysRoleDao;
import com.cn.springbootjpa.user.entity.TmSysRole;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TmSysRoleBoImpl extends BaseBoImpl<TmSysRole, Integer> implements TmSysRoleBo {
	@Autowired
	private TmSysRoleDao dao;

	@Override
	public BaseDao<TmSysRole, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
