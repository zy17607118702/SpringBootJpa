/**
 * 
 */
package com.cn.springbootjpa.user.bo;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

/**
 * @author zhangyang
 *
 */

public interface TmSysUserBo  extends BaseBo<TmSysUser, Integer>{
	public TmSysUser checkPassword(String userName,String password);
}
