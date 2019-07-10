/**
 * 
 */
package com.cn.springbootjpa.mq.msgbo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.mq.msgbo.TiIfsMqsOutMsgBo;
import com.cn.springbootjpa.mq.msgdao.TiIfsMqsOutMsgDao;
import com.cn.springbootjpa.mq.msgentity.TiIfsMqsOutMsg;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TiIfsMqsOutMsgBoImpl extends BaseBoImpl<TiIfsMqsOutMsg, Integer> implements TiIfsMqsOutMsgBo {
	@Autowired
	private TiIfsMqsOutMsgDao dao;

	@Override
	public BaseDao<TiIfsMqsOutMsg, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
