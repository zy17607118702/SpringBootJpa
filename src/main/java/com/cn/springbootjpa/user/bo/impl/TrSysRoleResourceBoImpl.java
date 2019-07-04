/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.bo.TrSysRoleResourceBo;
import com.cn.springbootjpa.user.dao.TrSysRoleResourceDao;
import com.cn.springbootjpa.user.entity.TrSysRoleResource;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TrSysRoleResourceBoImpl extends BaseBoImpl<TrSysRoleResource, Integer> implements TrSysRoleResourceBo {
	@Autowired
	private TrSysRoleResourceDao dao;

	@Override
	public BaseDao<TrSysRoleResource, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
