/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames="tmSysUserBo")
public interface TmSysUserBo  extends BaseBo<TmSysUser, Integer>{
	@Cacheable(value="#root.methodName+#p0+#p1")
	public TmSysUser checkPassword(String userName,String password);
	 
	public TmSysUser findByUsername(String userName);
	
	public void updatePwd(String username,String pwd);
}
