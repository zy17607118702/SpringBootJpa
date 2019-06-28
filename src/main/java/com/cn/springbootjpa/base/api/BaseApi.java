

package com.cn.springbootjpa.base.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.springbootjpa.base.common.PageRequest;
import com.cn.springbootjpa.base.common.PageResult;



/**
 * <p>
 * 基础微服务远程调用API。
 * </p>
 * <b>2018-06-20</b>
 *
 * @author danne<lshefan@163.com>
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseApi<T, ID extends Serializable> {

/**
* Creates an object.
*
* @param o
* @return
*/
@RequestMapping(value = "create", method = RequestMethod.POST)
T create(@RequestBody T o, HttpServletRequest request);

/**
* Create a list of object.
*
* @param list
* @return
*/
@RequestMapping(value = "createList", method = RequestMethod.POST)
Collection<T> createList(@RequestBody Collection<T> list);

/**
* Removes one object by given id.
*
* @param id
* @return
*/
@RequestMapping(value = "del/{id}", method = RequestMethod.GET)
ID delete(@PathVariable("id") ID id);

/**
* Sets enabled to given enabled value by given an array of ids.
*
* @param enabled
* @param ids
* @return
*/
@RequestMapping(value = "enable", method = RequestMethod.GET)
int enable(@RequestParam("enabled") boolean enabled, @RequestParam("ids") ID[] ids);

/**
* @param id
* @return
*/
@RequestMapping(value = "exists", method = RequestMethod.GET)
boolean exists(@RequestParam("id") ID id);

/**
* Return one object by given id.
*
* @param id
* @return
*/
@RequestMapping(value = "id/{id}", method = RequestMethod.GET)
T findById(@PathVariable("id") ID id);

/**
* Return a list of objects with given list of ids.
*
* @param ids
* @return
*/
@RequestMapping(value = "ids", method = RequestMethod.GET)
List<T> findByIds(@RequestParam("ids") ID[] ids);

/**
* @param query
* @param page
* @param size
* @return
*/
@RequestMapping(value = "list", method = RequestMethod.GET)
PageResult<T> list(@RequestParam Map<String, String> query, @RequestParam(name = "page", required = false) int page,
@RequestParam(name = "size", required = false) int size);

@RequestMapping(value = "list", method = { RequestMethod.POST })
PageResult<T> list(@RequestBody(required = false) PageRequest query);

/**
* @param o
* @return
*/
@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
void update(@ModelAttribute("m") @RequestBody T o, HttpServletRequest request);

}