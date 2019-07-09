/**
 * 
 */
package com.cn.springbootjpa.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.code.CodeNameItem;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.user.bo.TmSysResourceBo;
import com.cn.springbootjpa.user.entity.TmSysResource;
import com.cn.springbootjpa.user.vo.Resource;
import com.cn.springbootjpa.util.LoginUserUtil;
import com.cn.springbootjpa.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**R
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
	@ApiOperation(value = "菜单种类下拉框", notes = "获取不同应用类型的菜单类型")
	@GetMapping("/resourcetype")
	@ApiImplicitParam(name = "type", value = "应用类型", required = true, dataType = "String")
	public List<CodeNameItem> getResourceTypes(@RequestParam("type") String resourceType){
        List<CodeNameItem> items = new ArrayList<CodeNameItem>();
        if (StringUtil.isNullOrBlank(resourceType)) {
            return null;
        }
        if (Resource.TYPE_RICHCLIENT.equals(resourceType)) {//rcp端
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_CATEGORY, "资源分类", null));
        } else if (Resource.TYPE_UI_CATEGORY.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_CATEGORY, "资源分类", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_VIEW, "视图", null));
        } else if (Resource.TYPE_UI_VIEW.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_VIEW, "视图", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_ACTION, "Action", null));
        } else if (Resource.TYPE_UI_ACTION.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_UI_ACTION, "Action", null));
        } else if (Resource.TYPE_WEB.equals(resourceType)) {//web端
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_CAT, "Web资源分区", null));
        } else if (Resource.TYPE_WEB_CAT.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_CAT, "Web资源分类", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_FUNCTION, "Web功能模块", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_URL, "Web URL资源", null));
        } else if (Resource.TYPE_WEB_FUNCTION.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_FUNCTION, "Web功能模块", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_URL, "Web URL资源", null));
        } else if (Resource.TYPE_WEB_URL.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_WEB_URL, "Web URL资源", null));
        } else if (Resource.TYPE_DEVICE.equals(resourceType)) {//pad端
            items.add(new CodeNameItem(null, null, Resource.TYPE_DEVICE_CAT, "Device资源分类", null));
        } else if (Resource.TYPE_DEVICE_CAT.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_DEVICE_CAT, "Device资源分类", null));
            items.add(new CodeNameItem(null, null, Resource.TYPE_DEVICE_VIEW, "Device视图", null));
        } else if (Resource.TYPE_DEVICE_VIEW.equals(resourceType)) {
            items.add(new CodeNameItem(null, null, Resource.TYPE_DEVICE_VIEW, "Device视图", null));
        }
        return items;
	}
	
	@GetMapping("/web/resources")
	@ApiOperation(value = "web菜单", notes = "获取用户对应权限的web菜单")
	public List<Resource>  getWebResourceList() {
		String username=LoginUserUtil.getLoginUser();
		List<TmSysResource> resourceList = tmSysResourceBo.findResourceList(username, Resource.TYPE_WEB_URL);
		if(CollectionUtils.isEmpty(resourceList))
			return null;
		return tmSysResourceBo.findAllResources(resourceList);
	}
	
	@GetMapping("/device/resources")
	@ApiOperation(value = "pad菜单", notes = "获取用户对应权限的pad菜单")
	public List<Resource>  getDeviceResourceList() {
		String username=LoginUserUtil.getLoginUser();
		List<TmSysResource> resourceList = tmSysResourceBo.findResourceList(username, Resource.TYPE_DEVICE);
		if(CollectionUtils.isEmpty(resourceList))
			return null;
		return tmSysResourceBo.findAllResources(resourceList);
	}
}
