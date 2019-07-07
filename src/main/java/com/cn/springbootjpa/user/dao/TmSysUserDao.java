package com.cn.springbootjpa.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TmSysUser;

@Repository
public interface TmSysUserDao extends BaseDao<TmSysUser, Integer> {
	@Query(value = "select * from tm_sys_user t where t.user_name=?1 and t.user_pwd=?2", nativeQuery = true)
	public TmSysUser checkPassword(String userName, String password);

	@Query(value = "select * from tm_sys_user t where t.user_name=?1", nativeQuery = true)
	public TmSysUser findByUsername(String userName);

	@Modifying
	@Query("update TmSysUser set userPwd=:pwd where userName=:username ")
	public void updatePwd(@Param("username") String username, @Param("pwd") String pwd);

	@Query(value = "select u.* from tm_sys_user u left join tr_sys_user_role ur on u.tm_sys_user_id=ur.tm_sys_user_id "
			+ "left join tm_sys_role r on ur.tm_sys_role_id=r.tm_sys_role_id " + "where r.role_code =:roleCode "
			+ "and u.is_locked!=1 and u.mark_status=1 ", nativeQuery = true)
	public List<TmSysUser> findRoleUsers(@Param("roleCode") String roleCode);

	@Query(value = "select t.* from tm_sys_user t where t.tm_sys_user_id not in ( "
			+ "select ur.tm_sys_user_id from tr_sys_user_role ur,tm_sys_role r "
			+ "where ur.tm_sys_role_id=r.tm_sys_role_id and r.role_code=:roleCode "
			+ ") and t.mark_status=1 and t.is_locked!=1 ", nativeQuery = true)
	public List<TmSysUser> findUserNoRoles(@Param("roleCode") String roleCode);
}
