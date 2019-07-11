package com.cn.springbootjpa.system.dao.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.entity.user.TrSysRoleResource;

@Repository
public interface TrSysRoleResourceDao extends BaseDao<TrSysRoleResource, Integer> {
	/**
	 * 根据用户角色获取对应的资源菜单
	 * 
	 * @param roles
	 * @return
	 */
	@Query(value = "select t.res_path from  tm_sys_resource t,tm_sys_role r ,tr_sys_role_resource tr "
			+ "where t.tm_sys_resource_id=tr.tm_sys_resource_id and r.tm_sys_role_id=tr.tm_sys_role_id "
			+ "and r.role_code in(:roles)", nativeQuery = true)
	public List<String> findRoleResources(@Param("roles") Set<String> roles);

}
