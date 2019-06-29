package com.cn.springbootjpa.base.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Id;
import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.Result;
import com.cn.springbootjpa.base.common.ResultUtil;
import com.cn.springbootjpa.base.common.page.PageReq;
import com.cn.springbootjpa.base.common.page.PageRes;
import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.entity.BaseEntity;
import com.cn.springbootjpa.base.exception.AppException;


public abstract class BaseController<T extends BaseEntity, ID extends Serializable> {
	
	protected abstract BaseBo<T, ID> getBo();
	private static final Map<Class<?>, String> idFields = new HashMap<>();
	
	/**
	 * 获取查询条件集合sql
	 * @param condition
	 * @param pageReq
	 * @return
	 */
	@GetMapping(value="list")
	public PageRes<T> page(T condition, PageReq pageReq){
		Pageable pageable = PageReq.getPageable(pageReq);
		Page<T> findAll = getBo().findAll(condition, pageable);
		
		return PageRes.toRes(findAll);
	}
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	@GetMapping(value="id/{id}")
	public Result<T> getById(@PathVariable ID id){
		Optional<T> findById = getBo().getById(id);
		T result=null;
		if(findById.isPresent()) {
			result=findById.get();
		}else {
			throw new AppException("当前编号不存在");
		}
		return ResultUtil.success(result);
	}
	
	/**
	 * 新增方法
	 * @param model
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="create")
	public Result<T> add(@Valid T model, BindingResult bindingResult) throws Exception{
		setNewId(model);
		validateNewModel(model);
		T save = getBo().save(model);
		
		return ResultUtil.success(save);
	}
	
	@PostMapping(value="update")
	public Result<T> edit(@Valid T model, BindingResult bindingResult) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		validateModifyModel(model);
		T save = getBo().save(model);
		
		return ResultUtil.success(save);
	}
	
	@GetMapping(value="del")
	public Result<Boolean> delete(@PathVariable ID guid){
		getBo().delete(guid);
		
		return ResultUtil.success(true);
	}
	
	@SuppressWarnings("unchecked")
	protected void validateNewModel(T model) throws Exception {
		long count = 0;
		Map<String, String> validatesFields = validatesDuplicationFields();
		for (String field : validatesFields.keySet()) {
			Field declaredField = model.getClass().getDeclaredField(field);
			declaredField.setAccessible(true);
			Object code = declaredField.get(model);
			
			T example = (T) model.getClass().newInstance();
			declaredField.set(example, code);
			ExampleMatcher match = ExampleMatcher.matching().withStringMatcher(StringMatcher.DEFAULT);
			Example<T> of = Example.of(example, match);
			count = getBo().count(of);
			
			if(count > 0) {
				throw new AppException("数据库已存在，请修改");
			}
		}
	}

	protected abstract void setNewId(T model);
	

	
	protected void validateModifyModel(T model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		long count = 0;
		Class<? extends Object> class1 = model.getClass();
		String idField = getEntityIdField(class1);
		Field declaredFieldGuid = class1.getDeclaredField(idField);
		Map<String, String> validatesFields = validatesDuplicationFields();
		for (String field : validatesFields.keySet()) {
			Field declaredFieldCode = class1.getDeclaredField(field);
			declaredFieldGuid.setAccessible(true);
			declaredFieldCode.setAccessible(true);
			Object guid = declaredFieldGuid.get(model);
			Object code = declaredFieldCode.get(model);
			List<QueryCondition> conditions = new ArrayList<>();
			conditions.add(QueryCondition.notEq(idField, guid));
			conditions.add(QueryCondition.eq(field, code));
			count = getBo().count(conditions);
			if(count > 0) {
				throw new AppException("数据库已存在，请修改");
			}
		}
	}
	
	/**
	 * 如果需要验证字段重复,给出需要验证的field的字段和提示信息
	 * @return
	 */
	protected Map<String, String> validatesDuplicationFields(){
		Map<String, String> validates = new HashMap<>();
		return validates;
	}

	private String getEntityIdField(Class<? extends Object> class1) {
		if(!idFields.containsKey(class1)) {
			Field[] declaredFields = class1.getDeclaredFields();
			String idField = "guid";
			for (Field field : declaredFields) {
				Id annotation = field.getAnnotation(Id.class);
				if(annotation != null) {
					idField = field.getName();
					break;
				}
			}
			idFields.put(class1, idField);
		}
		
		return idFields.get(class1);
	}

}
