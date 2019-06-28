

package com.cn.springbootjpa.base.api;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.bo.BaseBO;
import com.cn.springbootjpa.base.common.PageRequest;
import com.cn.springbootjpa.base.common.PageResult;
import com.cn.springbootjpa.base.controller.BaseRestController;
import com.cn.springbootjpa.base.entity.BaseEntity;
import com.cn.springbootjpa.base.exception.AppException;
import com.cn.springbootjpa.util.ConvertUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Base restful controller for crud.
 * </p>
 * <b>2018-04-18</b>
 *
 * @author danne<lshefan@163.com>
 *
 * @param <T>
 * @param <Long>
 */
@Slf4j
@RestController
public abstract class BaseCrudRestController<T extends BaseEntity> extends BaseRestController
        implements BaseApi<T, Long> {
    protected final static String getLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof AbstractAuthenticationToken) {
                return ((AbstractAuthenticationToken) authentication).getName();
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return ((UsernamePasswordAuthenticationToken) authentication).getName();
            } else if (authentication instanceof RememberMeAuthenticationToken) {
                return ((UserDetails) ((RememberMeAuthenticationToken) authentication).getPrincipal()).getUsername();
            }
        }
        return null;
    }

    private Class<T> entityClass;

    @Autowired
    private ConversionService conversionService;

    @SuppressWarnings("unchecked")
    public BaseCrudRestController() {
        super();
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        entityClass = (Class<T>) types[0];
    }

    protected DetachedCriteria buildCriteria(PageRequest query) {
        preBuildCriteria(query);
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass, "a");
        if (query != null) {
            if (!query.isEmpty()) {
                final Conjunction conj = Restrictions.conjunction();
                for (Map.Entry<String, ?> entry : query.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null || StringUtils.isBlank(value.toString())) {
                        continue;
                    }
                    if (key.contains(".")) {
                        String[] str = key.split("[.]");
                        Field field = null;
                        Class<?> calzz = entityClass;
                        String aliasName = "t";
                        for (int i = 0; i < str.length; i++) {
                            if (str.length - 1 != i) {
                                if (i == 0) {
                                    criteria.createAlias(str[i], aliasName + i);
                                    aliasName = aliasName + i;
                                } else {
                                    if (str.length - 1 != i) {
                                        criteria.createAlias(aliasName + "." + str[i], aliasName + i);
                                        aliasName = aliasName + i;
                                    }
                                }
                            }
                            field = ReflectionUtils.findField(calzz, str[i]);
                            calzz = field.getType();
                        }
                        criteria.add(Restrictions.eq(aliasName + "." + str[str.length - 1], convert(field, value)));
                    } else {
                        Field field = ReflectionUtils.findField(entityClass, key);
                        if (field != null) {
                            conj.add(Restrictions.eq(key, convert(field, value)));
                        }
                    }
                }
                criteria.add(conj);
            }
            List<Map<String, Object>> sort = (List<Map<String, Object>>) query.get("sort");
            if (sort != null && !sort.isEmpty()) {
                sort.forEach(s -> {
                    String prop = (String) s.get("prop");
                    String order = (String) s.get("order");
                    boolean acending = !"descending".equalsIgnoreCase(order);
                    criteria.addOrder(acending ? org.hibernate.criterion.Order.asc(prop)
                            : org.hibernate.criterion.Order.desc(prop));
                });
            }

        }
        return criteria;
    }

    private Object convert(Field field, Object obj) {
        return conversionService.convert(obj, field.getType());
    }

    @Override
    @PostMapping(value = "create")
    public T create(@RequestBody T o, HttpServletRequest request) {
        preSave(o, request);
        return getBo().create(o);
    }

    @Override
    @PostMapping(value = "createList")
    public Collection<T> createList(@RequestBody Collection<T> list) {
        return getBo().create(list);
    }

    @Override
    @GetMapping(value = "del/{id}")
    public Long delete(@PathVariable("id") Long id) {
        if (id != null) {
            try {
                T m = getBo().getById(id);
                getBo().delete(m);
            } catch (Exception e) {
                throw new AppException("数据记录仍然被其他数据引用或违反唯一键约束，无法执行删除操作");
            }
            return id;
        }
        return null;
    }

    @PostMapping(value = "del")
    public Long delete(@RequestBody Long[] ids) {
        // Long[] ids = ConvertUtils.convertToLongArray(body.get("ids"));
        if (ids != null && ids.length > 0) {
            try {
                for (Long id : ids) {
                    getBo().delete(id);
                }
            } catch (Exception e) {
                throw new AppException("数据记录仍然被其他数据引用或违反唯一键约束，无法执行删除操作");
            }
            return Long.getLong(ids.length + "");
        }
        return null;
    }

    @Override
    @GetMapping(value = "enable")
    public int enable(@RequestParam("enabled") boolean enabled, @RequestParam("ids") Long[] ids) {
        return 0;
    }

    @Override
    @GetMapping(value = "exists")
    public boolean exists(@RequestParam("id") Long id) {
        return false;
    }

    // @ApiOperation("根据Long获取数据")
    // @ApiResponse(code = 200, message = "Long值对应数据记录")
    // @PostMapping(value = "{id}")
    // FIXME: 返回未单一泛型结果时，如果这里标注，会发现mvc request
    // mapping无法建立，而其它方法则不标注不有问题，原因未明.2018-09-07
    @Override
    public T findById(@PathVariable("id") Long id) {
        return getBo().getById(id);
    }

    @Override
    @GetMapping(value = "ids")
    public List<T> findByIds(@RequestParam("ids") Long[] ids) {
        return null;
    }

    protected abstract BaseBO<T> getBo();

    /**
     * 共享的验证规则 验证失败返回true
     * 
     * @param m
     * @param result
     * @return
     */
    protected boolean hasError(T m, BindingResult result) {
        Assert.notNull(m, "Model attribute cannot be null!");
        return result.hasErrors();
    }

    @Override
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public PageResult<T> list(@RequestParam Map<String, String> query,
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "size", required = false) int size) {
        PageRequest request = new PageRequest();
        request.putAll(query);
        return this.list(request);
    }

    @Override
    @RequestMapping(value = "list", method = { RequestMethod.POST })
    public PageResult<T> list(@RequestBody(required = false) PageRequest query) {
        if (query == null) {
            query = new PageRequest();
        }
        int page = query.get("page") == null ? 1 : ConvertUtils.convertObjectToInteger(query.get("page"));
        if (page <= 1) {
            page = 1;
        }
        int size = query.get("limit") == null ? 0 : ConvertUtils.convertObjectToInteger(query.get("limit"));
        if (size <= 0) {
            size = 20;
        }
        DetachedCriteria criteria = buildCriteria(query);
        long total = getBo().countByCriteria(criteria);
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);

        PageResult<T> result = new PageResult<>();
        List<T> content = total > 0 ? getBo().findByCriteria(criteria, (page - 1) * size, size) : null;
        result.setTotal(total);
        result.setContent(content);

        return result;
    }

    /**
     * 创建新的model实例，重写可添加初始值等初始化工作。
     *
     * @return model实例
     */
    protected T newModel(HttpServletRequest request) {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 
     *
     * @param query
     */
    protected void preBuildCriteria(PageRequest query) {
        Arrays.asList("limit", "page", "size", "m").forEach(key -> query.remove(key));
        Iterator<Entry<String, Object>> it = query.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> next = it.next();
            if (next == null || next.getValue() == null || StringUtils.isAllBlank(next.getValue().toString())) {
                it.remove();
            }
        }
    }

    protected void preSave(T m, HttpServletRequest request) {

    }

    /**
     * save方法验证通过后，调用的直接保存模型记录方法。
     *
     * @param m
     * @param request
     * @param redirectAttributes
     */
    protected T saveModel(T m, HttpServletRequest request) {
        preSave(m, request);
        return getBo().create(m);
    }

    @Override
    @PostMapping({ "update", "update/{id}" })
    public void update(@RequestBody T o, HttpServletRequest request) {
        preSave(o, request);
        getBo().update(o);
    }

}