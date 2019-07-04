/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheConfig;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.user.entity.TrSysRoleResource;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * 
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames = "trSysRoleResource")
public interface TrSysRoleResourceBo extends BaseBo<TrSysRoleResource, Integer> {
	/**
	 * 根据用户角色获取对应的资源菜单
	 * @param roles
	 * @return
	 */
	public List<String> findRoleResources(Set<String> roles);
}
