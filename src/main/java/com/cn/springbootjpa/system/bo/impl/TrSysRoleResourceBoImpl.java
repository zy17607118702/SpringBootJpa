/**
 * 
 */
package com.cn.springbootjpa.system.bo.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.bo.TrSysRoleResourceBo;
import com.cn.springbootjpa.system.dao.TrSysRoleResourceDao;
import com.cn.springbootjpa.system.entity.TrSysRoleResource;

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

	@Override
	public List<String> findRoleResources(Set<String> roles) {
		// TODO Auto-generated method stub
		return dao.findRoleResources(roles);
	}
	
}
