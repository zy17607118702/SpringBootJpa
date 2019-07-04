/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import org.springframework.cache.annotation.CacheConfig;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.user.entity.TmSysRole;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * 
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames = "tmSysRoleBo")
public interface TmSysRoleBo extends BaseBo<TmSysRole, Integer> {
}
