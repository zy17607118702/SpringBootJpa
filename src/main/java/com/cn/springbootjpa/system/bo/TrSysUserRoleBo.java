/**
 * 
 */
package com.cn.springbootjpa.system.bo;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.system.entity.TrSysUserRole;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * 
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames = "trSysUserRole")
public interface TrSysUserRoleBo extends BaseBo<TrSysUserRole, Integer> {
	/**
	 * 通过用户获取用户的角色信息
	 * @param userName
	 * @return
	 */
	public List<String> findUserRoles(String userName);
}
