/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.bo.TrSysUserRoleBo;
import com.cn.springbootjpa.user.dao.TrSysUserRoleDao;
import com.cn.springbootjpa.user.entity.TrSysUserRole;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TrSysUserRoleBoImpl extends BaseBoImpl<TrSysUserRole, Integer> implements TrSysUserRoleBo {
	@Autowired
	private TrSysUserRoleDao dao;

	@Override
	public BaseDao<TrSysUserRole, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public List<String> findUserRoles(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
