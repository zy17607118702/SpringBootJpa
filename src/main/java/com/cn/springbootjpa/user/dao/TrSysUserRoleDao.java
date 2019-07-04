package com.cn.springbootjpa.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TrSysUserRole;

@Repository
public interface TrSysUserRoleDao extends BaseDao<TrSysUserRole, Integer> {
	
	@Query(value="select t.role_code from tm_sys_role t ,tm_sys_user u,tr_sys_user_role ut " + 
			"where t.tm_sys_role_id=ut.tr_sys_user_role_id and u.tm_sys_user_id=ut.tm_sys_user_id " + 
			"and u.user_name=:userName",nativeQuery=true)
	public List<String> findUserRoles(String userName);
	
}
