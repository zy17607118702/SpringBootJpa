/**
 * 
 */
package com.cn.springbootjpa.system.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.bo.TiIfsMqsInMsgBo;
import com.cn.springbootjpa.system.dao.TiIfsMqsInMsgDao;
import com.cn.springbootjpa.system.entity.TiIfsMqsInMsg;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TiIfsMqsInMsgBoImpl extends BaseBoImpl<TiIfsMqsInMsg, Integer> implements TiIfsMqsInMsgBo {
	@Autowired
	private TiIfsMqsInMsgDao dao;

	@Override
	public BaseDao<TiIfsMqsInMsg, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
