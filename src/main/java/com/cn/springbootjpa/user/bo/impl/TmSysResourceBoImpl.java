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
import com.cn.springbootjpa.user.bo.TmSysResourceBo;
import com.cn.springbootjpa.user.dao.TmSysResourceDao;
import com.cn.springbootjpa.user.entity.TmSysResource;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TmSysResourceBoImpl extends BaseBoImpl<TmSysResource, Integer> implements TmSysResourceBo {
	@Autowired
	private TmSysResourceDao dao;

	@Override
	public BaseDao<TmSysResource, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	/* (non-Javadoc)
	 * @see com.cn.springbootjpa.user.bo.TmSysResourceBo#findResourceList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TmSysResource> findResourceList(String userName, String resType) {
		// TODO Auto-generated method stub
		return dao.findResourceList(userName, resType);
	}
	
}
