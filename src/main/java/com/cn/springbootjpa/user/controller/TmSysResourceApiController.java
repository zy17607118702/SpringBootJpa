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
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.code.CodeNameItem;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.user.bo.TmSysResourceBo;
import com.cn.springbootjpa.user.entity.TmSysResource;
import com.cn.springbootjpa.user.vo.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色页面增删改查
 * 
 * @author zhangyang
 *
 */
@Api(value = "菜单 主数据增删改查", description = "详细描述")
@RestController
@RequestMapping(value = "resource")
public class TmSysResourceApiController extends BaseController<TmSysResource, Integer> {
	@Autowired
	private TmSysResourceBo tmSysResourceBo;
	
	@Override
	protected BaseBo<TmSysResource, Integer> getBo() {
		// TODO Auto-generated method stub
		return tmSysResourceBo;
	}

	/**
	 * 鉴别唯一性方法
	 */
	@Override
	protected Map<String, Object> validateUnique(TmSysResource t) {
		Map<String, Object> result = new HashMap<>();
		result.put(TmSysResource.FIELD_RESTYPE, t.getResType());
		result.put(TmSysResource.FIELD_RESNAMEC, t.getResNameC());
		return result;
	}

	/**
	 *所有菜单类型下拉框
	 * @return
	 */
	public List<CodeNameItem> getResourceTypes(){
		return null;
	}
	
	@GetMapping("/web/resources")
	@ApiOperation(value = "web菜单", notes = "获取用户对应权限的web菜单")
	public Resource getWebResourceList() {
		//String username=LoginUserUtil.getLoginUser();
		
		return null;
	}
}
