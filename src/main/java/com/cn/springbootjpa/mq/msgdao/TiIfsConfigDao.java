package com.cn.springbootjpa.mq.msgdao;

import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.mq.msgentity.TiIfsConfig;

@Repository
public interface TiIfsConfigDao extends BaseDao<TiIfsConfig, Integer> {
	
}
