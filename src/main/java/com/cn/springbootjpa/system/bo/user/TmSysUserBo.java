/**
 * 
 */
package com.cn.springbootjpa.system.bo.user;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.query.ImportResult;
import com.cn.springbootjpa.system.entity.user.TmSysUser;
import com.cn.springbootjpa.system.importvo.TmSysUserVo;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * @author zhangyang
 *
 */

@CacheConfig(cacheNames="tmSysUserBo")
public interface TmSysUserBo  extends BaseBo<TmSysUser, Integer>{
	/**
	 * 根据用户账号密码获取对应用户
	 * @param userName
	 * @param password
	 * @return
	 */
	@Cacheable(value="#root.methodName+#p0+#p1")
	public TmSysUser checkPassword(String userName,String password);
	/**
	 * 根据用户名获取对应用户
	 * @param userName
	 * @return
	 */
	public TmSysUser findByUsername(String userName);
	/**
	 * 修改密码方法
	 * @param username
	 * @param pwd
	 */
	public void updatePwd(String username,String pwd);
	/**
	 * 用户信息导入
	 * @param list
	 * @param isUpdate
	 * @return
	 */
	public ImportResult<String> importVo(List<TmSysUserVo> list,boolean isUpdate);
	/**
	 * 根据角色信息获取角色下对应用户
	 * @param roles
	 * @return
	 */
	public List<TmSysUser> findRoleUsers(String roleCode);
	/**
	 * 获取不具有当前角色信息的用户
	 * @param roleCode
	 * @return
	 */
	public List<TmSysUser> findUserNoRoles(String roleCode);
}
