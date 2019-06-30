/**
 * 
 */
package com.cn.springbootjpa.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.master.bo.TmSysUserBo;
import com.cn.springbootjpa.master.entity.TmSysUser;
import com.cn.springbootjpa.util.MD5;

/**
 * @author zhangyang
 *
 */
@RestController
@RequestMapping(value="user")
public class TmSysUserApiController extends BaseController<TmSysUser, Integer> {
	@Autowired
	private TmSysUserBo tmSysUserBo;

	@Override
	protected BaseBo<TmSysUser, Integer> getBo() {
		// TODO Auto-generated method stub
		return tmSysUserBo;
	}

	@Override
	public void preSave(TmSysUser model) {
		//用户加密
		model.setUserPwd(MD5.md5(model.getUserPwd()));
	}
	
}
