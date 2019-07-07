/**
 * 
 */
package com.cn.springbootjpa.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.user.bo.TmSysRoleBo;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysRole;
import com.cn.springbootjpa.user.entity.TmSysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 角色页面增删改查
 * 
 * @author zhangyang
 *
 */
@Api(value = "角色 主数据增删改查", description = "详细描述")
@RestController
@RequestMapping(value = "role")
public class TmSysRoleApiController extends BaseController<TmSysRole, Integer> {
	@Autowired
	private TmSysRoleBo tmSysRoleBo;
	@Autowired
	private TmSysUserBo tmSysUserBo;
	
	@Override
	protected BaseBo<TmSysRole, Integer> getBo() {
		// TODO Auto-generated method stub
		return tmSysRoleBo;
	}

	/**
	 * 鉴别唯一性方法
	 */
	@Override
	protected Map<String, Object> validateUnique(TmSysRole t) {
		Map<String, Object> result = new HashMap<>();
		result.put(TmSysRole.FIELD_ROLECODE, t.getRoleCode());
		return result;
	}

	@GetMapping("findroleusers")
	@ApiOperation(value = "获取角色下用户信息", notes = "根据角色获取角色相应用户")
	@ApiImplicitParam(name = "roleCode", value = "角色编号", required = true, dataType = "String")
	public List<TmSysUser> findRoleUsers(@RequestParam("roleCode") String roleCode){
		return tmSysUserBo.findRoleUsers(roleCode);
	}
}
