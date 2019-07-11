package com.cn.springbootjpa.system.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.system.entity.user.TmSysResource;

@Repository
public interface TmSysResourceDao extends BaseDao<TmSysResource, Integer> {
	/**
	 * 根据用户类型获取不同设备下的菜单集合
	 * @param userName
	 * @param resType
	 * @return
	 */
	@Query(value="SELECT re.* FROM tr_sys_role_resource rr " + 
			"	LEFT JOIN tm_sys_resource re ON rr.tm_sys_resource_id = re.tm_sys_resource_id " + 
			"	LEFT JOIN tm_sys_role ro ON ro.tm_sys_role_id = rr.tm_sys_role_id " + 
			"	LEFT JOIN tr_sys_user_role ur ON ur.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tm_sys_user ue ON ue.tm_sys_user_id = ur.tm_sys_user_id  " + 
			"WHERE ue.system_name = :userName AND re.res_type = :resType ",nativeQuery=true)
	public List<TmSysResource> findResourceList(@Param("userName")String userName,@Param("resType")String resType);
	
	/**
	 * 获取所有的菜单总类集合 
	 * 按照等级和上级信息排序
	 * @param resources
	 * @return
	 */
	@Query(value="select * from ( " + 
			"select * from tm_sys_resource tttt where tttt.tm_sys_resource_id in( " + 
			"select DISTINCT ttt.parent_res_id from tm_sys_resource ttt where ttt.tm_sys_resource_id in ( " + 
			"select DISTINCT t.parent_res_id from tm_sys_resource t where t.tm_sys_resource_id in ( " + 
			"select DISTINCT tt.parent_res_id from tm_sys_resource tt where tt.tm_sys_resource_id in (:resources) " + 
			"))) " + 
			"UNION all " + 
			"select * from tm_sys_resource ttt where ttt.tm_sys_resource_id in ( " + 
			"select DISTINCT t.parent_res_id from tm_sys_resource t where t.tm_sys_resource_id in ( " + 
			"select DISTINCT  tt.parent_res_id from tm_sys_resource tt where tt.tm_sys_resource_id in (:resources) " + 
			")) " + 
			"UNION all " + 
			"select * from tm_sys_resource t where t.tm_sys_resource_id in ( " + 
			"select DISTINCT tt.parent_res_id from tm_sys_resource tt where tt.tm_sys_resource_id in (:resources) " + 
			") UNION all " + 
			"select tt.* from tm_sys_resource tt where tt.tm_sys_resource_id in (:resources) " + 
			") t1 order by t1.res_level asc,t1.parent_res_id asc",nativeQuery=true)
	public List<TmSysResource> findAllResources(@Param("resources")List<Integer> resources);
}
