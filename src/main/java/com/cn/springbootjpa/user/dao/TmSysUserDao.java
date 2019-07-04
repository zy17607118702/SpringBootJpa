package com.cn.springbootjpa.user.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TmSysUser;

@Repository
public interface TmSysUserDao extends BaseDao<TmSysUser, Integer> {
    @Query(value="select * from tm_sys_user t where t.user_name=?1 and t.user_pwd=?2",nativeQuery=true)
	public TmSysUser checkPassword(String userName,String password);
    
    @Query(value="select * from tm_sys_user t where t.user_name=?1",nativeQuery=true)
    public TmSysUser findByUsername(String userName); 
}
