package com.cn.springbootjpa.mq.msgdao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.mq.msgentity.TiIfsMqsOutMsg;

@Repository
public interface TiIfsMqsOutMsgDao extends BaseDao<TiIfsMqsOutMsg, Integer> {
	
}
