package com.cn.springbootjpa.base.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Id;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.page.PageReq;
import com.cn.springbootjpa.base.common.page.PageRes;
import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.common.query.QueryParam;
import com.cn.springbootjpa.base.common.query.Result;
import com.cn.springbootjpa.base.common.query.ResultUtil;
import com.cn.springbootjpa.base.entity.BaseEntity;
import com.cn.springbootjpa.base.exception.AppException;
import com.cn.springbootjpa.base.exception.ApplicationException;
import com.cn.springbootjpa.util.excel.FileView;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public abstract class BaseController<T extends BaseEntity, ID> extends BaseRestController {

	protected abstract BaseBo<T, ID> getBo();

	private static final Map<Class<?>, String> idFields = new HashMap<>();

	/**
	 * 动态拼接条件查询sql集合
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询页面主体数据", notes = "根据条件动态查询数据集")
	@ApiImplicitParam(name = "request", value = "查询条件集合", required = true, dataType = "JSON")
	@PostMapping(value = "list")
	public PageRes<T> list(@RequestBody QueryParam request) {

		PageReq pageReq = preBuildCriteria(request);
		List<QueryCondition> criteria = buildCriteria(request);
		long count = getBo().count(criteria);
		Page<T> findAll = null;
		if (count > 0) {
			findAll = getBo().findAll(criteria, PageReq.getPageable(pageReq));
			afterSearch(findAll);
		}
		return PageRes.toRes(findAll);
	}

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "id/{id}")
	@ApiOperation(value = "根据页面传入的id获取详情", notes = "精确查找数据")
	@ApiImplicitParam(paramType = "path", name = "id", value = "编号", required = true, dataType = "Long")
	public Result<T> getById(@PathVariable ID id) {
		Optional<T> findById = getBo().getById(id);
		T result = null;
		if (findById.isPresent()) {
			result = findById.get();
		} else {
			throw new AppException("AE0004");
		}
		afterGetById(result);
		return ResultUtil.success(result);
	}

	/**
	 * 新增方法
	 * 
	 * @param model
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "create")
	@ApiOperation(value = "新增方法", notes = "前端编辑对象新增到数据库")
	@ApiImplicitParam(name = "model", value = "新建实例", required = true, dataType = "JSON")
	public Result<T> create(@Valid @RequestBody T model, BindingResult bindingResult) throws Exception {
		preSave(model);
		validateNewModel(model);
		T save = getBo().save(model);
		return ResultUtil.success(save);
	}

	@PostMapping(value = "createList")
	@ApiOperation(value = "批量新增方法", notes = "批量新增对象到数据库")
	@ApiImplicitParam(name = "list", value = "新建对象集合", required = true, dataType = "JSON")
	public Collection<T> createList(@Valid @RequestBody Collection<T> list, BindingResult bindingResult)
			throws Exception {
		for (T model : list) {
			preSave(model);
			validateNewModel(model);
		}
		Collection<T> save = (Collection<T>) getBo().save(list);
		return save;
	}

	/**
	 * 修改方法
	 * 
	 * @param model
	 * @param bindingResult
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@PostMapping(value = "update")
	@ApiOperation(value = "单条修改方法", notes = "修改单条数据")
	@ApiImplicitParam(name = "model", value = "待修改对象", required = true, dataType = "JSON") 
	public Result<T> update(@Valid @RequestBody T model, BindingResult bindingResult)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		preSave(model);
		validateModifyModel(model);
		T save = getBo().save(model);

		return ResultUtil.success(save);
	}

	/**
	 * 单条数据删除
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "del/{id}")
	@ApiOperation(value = "单条删除方法", notes = "删除单条数据")
	@ApiImplicitParam(paramType = "path", name = "id", value = "待删除id", required = true, dataType = "Integer")
	public Result<Boolean> delete(@PathVariable ID id) {
		getBo().delete(id);

		return ResultUtil.success(true);
	}

	@PostMapping(value="del")
	@ApiOperation(value = "批量删除方法", notes = "批量删除数据")
	@ApiImplicitParam(name = "map", value = "需要删除的数组", required = true, dataType = "JSON")
	public Result<Boolean> delete( @RequestBody Map<String,ID[]> map) {
		//1.获取需要删除的集合
		ID[] ids = map.get("ids");
		List<QueryCondition> condition=new ArrayList<>();
		condition.add(QueryCondition.in("id", Arrays.asList(ids)));
		//查询这些集合是否都在数据库中
		long size = getBo().count(condition);
		if((int)size!=ids.length) {
			throw new ApplicationException("AE0007");
		}
		//删除对应数据
		List<T> findAll = getBo().findAll(condition);
		getBo().deleteInBatch(findAll);
		return ResultUtil.success(true);
	}

	/**
	 * 1.前端不同页面传入不同的name就能下载模板
	 * 2。后台模板位置规律/templates/{name}/{name}.xlsx 必须按照此规律布局模板
	 * @PathVariable 获取url上的请求参数
	 * @param name
	 * @return
	 */
	@GetMapping(value = { "/downloadXls" })
	@ApiOperation(value = "下载模板", notes = "下载模板方法")
	@ApiImplicitParam(name = "request", value = "查询条件集合", required = true, dataType = "JSON")
	public ModelAndView downloadXls(@RequestParam("name") String name) {
		StringBuilder path = new StringBuilder("/templates/");
		path.append(name).append("/").append(name).append(".xlsx");
		return new ModelAndView(new FileView(path.toString(), name));
	}
	
	
	/**
	 * 移除查询条件为空的参数 移除排序分页相关字段
	 * 
	 * @param pageReq
	 */
	public PageReq preBuildCriteria(QueryParam request) {
		PageReq req = new PageReq();
		Arrays.asList("sort", "sord", "page", "rows").forEach(item -> {
			if (request.containsKey("sort") && request.get("sort") instanceof String) {
				req.setSort(request.get("sort").toString());
				request.remove("sort");
			}
			if (request.containsKey("sord") && request.get("sord") instanceof Boolean) {
				req.setSord(Boolean.parseBoolean(request.get("sord").toString()));
				request.remove("sord");
			}
			if (request.containsKey("page") && request.get("page") instanceof Integer) {
				req.setPage(Integer.parseInt(request.get("page").toString()));
				request.remove("page");
			}
			if (request.containsKey("rows") && request.get("rows") instanceof Integer) {
				req.setRows(Integer.parseInt(request.get("rows").toString()));
				request.remove("rows");
			}
		});
		Iterator<Entry<String, Object>> it = request.entrySet().iterator();
		if (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			if (entry == null || entry.getValue() == null || StringUtils.isAllBlank(entry.getValue().toString())) {
				it.remove();
			}
		}

		return req;
	}

	/**
	 * 将每个查询参数拼接为condition条件
	 * 
	 * @param request
	 * @return
	 */
	public List<QueryCondition> buildCriteria(QueryParam request) {
		List<QueryCondition> list = new ArrayList<>();
		Set<String> keySet = request.keySet();
		QueryCondition condition = null;
		for (String key : keySet) {
			condition = QueryCondition.eq(key, request.get(key));
			list.add(condition);
		}
		return list;
	}

	/**
	 * 将页面传递的参数进行初步数据处理
	 * 
	 * @param model
	 */
	public void preSave(T model) {
	}

	/**
	 * 将数据库查询的数据二次筛选
	 * 
	 * @param findAll
	 */
	public void afterSearch(Page<T> findAll) {
	}

	/**
	 * 将数据库查询的数据二次筛选
	 * 
	 * @param findAll
	 */
	public void afterGetById(T t) {
	}

	/**
	 * 新增校验方法 校验不重复字段是否在数据库存在值
	 * 
	 * @param model
	 * @throws Exception
	 */
	protected void validateNewModel(T model) throws Exception {
		long count = 0;
		Map<String, Object> validatesFields = validateUnique(model);
		List<QueryCondition> conditions = new ArrayList<>();
		for (String field : validatesFields.keySet()) {
			Field declaredField = model.getClass().getDeclaredField(field);
			declaredField.setAccessible(true);
			Object code = declaredField.get(model);
			conditions.add(QueryCondition.eq(field, code));
		}
		count = getBo().count(conditions);
		if (count > 0) {
			throw new ApplicationException("AE0005");
		}
	}

	/**
	 * 校验重复字段方法 id不相等 其余判断唯一性的字段相等
	 * 
	 * @param model
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected void validateModifyModel(T model)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		long count = 0;
		Class<? extends Object> class1 = model.getClass();
		String idField = getEntityIdField(class1);
		Field declaredFieldid = class1.getDeclaredField(idField);
		declaredFieldid.setAccessible(true);
		Map<String, Object> validatesFields = validateUnique(model);
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(QueryCondition.notEq(idField, declaredFieldid.get(model)));
		for (String field : validatesFields.keySet()) {
			Field declaredFieldCode = class1.getDeclaredField(field);
			declaredFieldCode.setAccessible(true);
			Object code = declaredFieldCode.get(model);
			conditions.add(QueryCondition.eq(field, code));
		}
		count = getBo().count(conditions);
		if (count > 0) {
			throw new ApplicationException("AE0006");
		}
	}

	/**
	 * 需要在实体类被重写 非id字段判断唯一性
	 * 如果需要验证字段重复,给出需要验证的field的字段名和数值
	 * 
	 * @return
	 */
	protected abstract Map<String, Object> validateUnique(T t);

	private String getEntityIdField(Class<? extends Object> class1) {
		if (!idFields.containsKey(class1)) {
			Field[] declaredFields = class1.getDeclaredFields();
			String idField = "id";
			for (Field field : declaredFields) {
				Id annotation = field.getAnnotation(Id.class);
				if (annotation != null) {
					idField = field.getName();
					break;
				}
			}
			idFields.put(class1, idField);
		}

		return idFields.get(class1);
	}

}
