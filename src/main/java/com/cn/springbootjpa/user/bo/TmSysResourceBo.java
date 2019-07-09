/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.user.entity.TmSysResource;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * 
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames = "tmSysResourceBo")
public interface TmSysResourceBo extends BaseBo<TmSysResource, Integer> {
	/**
	 * 获取所有用户不同设备下的菜单连接集合
	 * @param userName
	 * @param resType
	 * @return
	 */
	public List<TmSysResource> findResourceList(String userName,String resType);
	/**
	 * 根据用户菜单集合获取所有的菜单信息
	 * 包含设备信息 1 2 3 级菜单
	 * @param resources
	 * @return
	 */
	public List<TmSysResource> findAllResources(List<TmSysResource> resources);
}
