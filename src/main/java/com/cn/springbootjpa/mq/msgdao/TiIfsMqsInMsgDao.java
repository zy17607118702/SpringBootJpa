package com.cn.springbootjpa.mq.msgdao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.mq.msgentity.TiIfsMqsInMsg;

@Repository
public interface TiIfsMqsInMsgDao extends BaseDao<TiIfsMqsInMsg, Integer> {
	
}
