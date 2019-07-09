/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * 获取所有用户不同设备下的菜单连接集合
	 * @param userName
	 * @param resType
	 * @return
	 */
	@Override
	public List<TmSysResource> findResourceList(String userName, String resType) {
		// TODO Auto-generated method stub
		return dao.findResourceList(userName, resType);
	}
	/**
	 * 根据用户菜单集合获取所有的菜单信息
	 * 包含设备信息 1 2 3 级菜单
	 * @param resources
	 * @return
	 */
	@Override
	public List<TmSysResource> findAllResources(List<TmSysResource> resources) {
		//遍历集合获取id组成新的集合
		List<Integer> collect = resources.stream().map(TmSysResource::getId).collect(Collectors.toList());
		return dao.findAllResources(collect);
	}
	
}
