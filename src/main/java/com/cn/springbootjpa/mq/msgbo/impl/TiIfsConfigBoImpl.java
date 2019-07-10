/**
 * 
 */
package com.cn.springbootjpa.mq.msgbo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.mq.msgbo.TiIfsConfigBo;
import com.cn.springbootjpa.mq.msgdao.TiIfsConfigDao;
import com.cn.springbootjpa.mq.msgentity.TiIfsConfig;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TiIfsConfigBoImpl extends BaseBoImpl<TiIfsConfig, Integer> implements TiIfsConfigBo {
	@Autowired
	private TiIfsConfigDao dao;

	@Override
	public BaseDao<TiIfsConfig, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
