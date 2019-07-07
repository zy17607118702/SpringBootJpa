/**
 * 
 */
package com.cn.springbootjpa.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.user.bo.TmSysResourceBo;
import com.cn.springbootjpa.user.entity.TmSysResource;

import io.swagger.annotations.Api;

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

}
