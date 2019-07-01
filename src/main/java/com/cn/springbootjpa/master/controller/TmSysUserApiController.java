/**
 * 
 */
package com.cn.springbootjpa.master.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.master.bo.TmSysUserBo;
import com.cn.springbootjpa.master.entity.TmSysUser;
import com.cn.springbootjpa.util.MD5;

import io.swagger.annotations.Api;

/**
 * @author zhangyang
 *
 */
@Api(value = "用户 主数据增删改查",description = "详细描述")
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

	@Override
	protected Map<String, Object> validateUnique(TmSysUser t) {
		Map<String,Object> result=new HashMap<>();
		result.put(TmSysUser.FIELD_USERNAME, t.getUserName());
		result.put(TmSysUser.FIELD_AGE, t.getAge());
		return result;
	}

//	@GetMapping(value="exception")
//	public void ExceptionTest() {
//		throw new ApplicationException("AE0001");
//	}
}
