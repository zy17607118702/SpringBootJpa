/**
 * 
 */
package com.cn.springbootjpa.system.bo.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.bo.user.TmSysRoleBo;
import com.cn.springbootjpa.system.dao.user.TmSysRoleDao;
import com.cn.springbootjpa.system.entity.user.TmSysRole;

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
