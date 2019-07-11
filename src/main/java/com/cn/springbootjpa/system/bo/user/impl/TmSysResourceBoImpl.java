/**
 * 
 */
package com.cn.springbootjpa.system.bo.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.bo.user.TmSysResourceBo;
import com.cn.springbootjpa.system.dao.user.TmSysResourceDao;
import com.cn.springbootjpa.system.entity.user.TmSysResource;
import com.cn.springbootjpa.system.vo.Resource;

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
	 * 
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
	 * 根据用户菜单集合获取所有的菜单信息 包含设备信息 1 2 3 级菜单
	 * 
	 * @param resources
	 * @return
	 */
	@Override
	public List<Resource> findAllResources(List<TmSysResource> resources) {
		// 遍历集合获取id组成新的集合
		List<Integer> collect = resources.stream().map(TmSysResource::getId).collect(Collectors.toList());
		List<TmSysResource> all = dao.findAllResources(collect);
		// 获取设备菜单作为返回值 res_level=0
		List<Resource> result = new ArrayList<Resource>();
		for (TmSysResource res : all) {
			Resource resource = new Resource();
			BeanUtils.copyProperties(res, resource);
			if (0 == res.getResLevel()) {
				result.add(resource);
			}
		}
		// 获取子菜单信息
		for (Resource res : result) {
			res.setResources(actResource(res.getId(), all));
		}
		return result;
	}

	/**
	 * 递归调用 获取设备菜单下的子菜单
	 * @param id
	 * @param resource
	 * @return
	 */
	public List<Resource> actResource(Integer id, List<TmSysResource> data) {
		List<Resource> childResource = null;
		if (CollectionUtils.isNotEmpty(data)) {
			childResource = new ArrayList<>();
		}
		for (TmSysResource item : data) {
			if (0 != item.getResLevel()) {
				if (id == item.getParentResid()) {
				   Resource res=new Resource();
				   BeanUtils.copyProperties(item, res);
				   childResource.add(res);
				}
			}

		}
		
		for(Resource res:childResource) {
			if(!res.getIsLeaf()) {
				res.setResources(actResource(res.getId(),data));
			}
		}
		return childResource;
	}
}
