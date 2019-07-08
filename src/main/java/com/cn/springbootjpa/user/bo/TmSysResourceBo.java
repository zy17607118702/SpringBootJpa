/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.query.Param;

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
	public List<TmSysResource> findResourceList(String userName,String resType);
}
