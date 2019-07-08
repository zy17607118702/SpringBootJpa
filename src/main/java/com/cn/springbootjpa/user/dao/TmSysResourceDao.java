package com.cn.springbootjpa.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.entity.TmSysResource;

@Repository
public interface TmSysResourceDao extends BaseDao<TmSysResource, Integer> {
	/**
	 * 根据用户类型获取不同设备下的菜单集合
	 * 菜单分为0 1 2 3 四级菜单 此为知道用户名和设备类型查询设备菜单
	 * @param userName
	 * @param resType
	 * @return
	 */
	@Query(value="select ttt.* from (SELECT	* FROM tm_sys_resource t WHERE t.tm_sys_resource_id IN ( " + 
			"SELECT DISTINCT t2.parent_res_id FROM tm_sys_resource t2 WHERE t2.tm_sys_resource_id IN ( " + 
			"SELECT t1.parent_res_id FROM tm_sys_resource t1 WHERE t1.tm_sys_resource_id IN ( " + 
			"SELECT DISTINCT re.parent_res_id FROM tr_sys_role_resource rr " + 
			"	LEFT JOIN tm_sys_resource re ON rr.tm_sys_resource_id = re.tm_sys_resource_id " + 
			"	LEFT JOIN tm_sys_role ro ON rr.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tr_sys_user_role ur ON ur.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tm_sys_user us ON us.tm_sys_user_id = ur.tm_sys_user_id  " + 
			"WHERE us.user_name = :userName AND re.res_type = :resType ) ) )  " + 
			"	UNION ALL " + 
			"SELECT t.* FROM tm_sys_resource t WHERE t.tm_sys_resource_id IN ( " + 
			"SELECT t1.parent_res_id FROM tm_sys_resource t1 WHERE t1.tm_sys_resource_id IN ( " + 
			"SELECT DISTINCT re.parent_res_id FROM tr_sys_role_resource rr " + 
			"	LEFT JOIN tm_sys_resource re ON rr.tm_sys_resource_id = re.tm_sys_resource_id " + 
			"	LEFT JOIN tm_sys_role ro ON rr.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tr_sys_user_role ur ON ur.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tm_sys_user us ON us.tm_sys_user_id = ur.tm_sys_user_id  " + 
			"WHERE us.user_name = :userName AND re.res_type = :resType ) )  " + 
			"	UNION ALL " + 
			"SELECT * FROM tm_sys_resource t  " + 
			"WHERE t.tm_sys_resource_id IN ( " + 
			"SELECT DISTINCT re.parent_res_id FROM tr_sys_role_resource rr " + 
			"	LEFT JOIN tm_sys_resource re ON rr.tm_sys_resource_id = re.tm_sys_resource_id " + 
			"	LEFT JOIN tm_sys_role ro ON rr.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tr_sys_user_role ur ON ur.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tm_sys_user us ON us.tm_sys_user_id = ur.tm_sys_user_id  " + 
			"WHERE us.user_name = :userName AND re.res_type = :resType )  " + 
			"	union all " + 
			"	SELECT DISTINCT re.* FROM tr_sys_role_resource rr " + 
			"	LEFT JOIN tm_sys_resource re ON rr.tm_sys_resource_id = re.tm_sys_resource_id " + 
			"	LEFT JOIN tm_sys_role ro ON rr.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tr_sys_user_role ur ON ur.tm_sys_role_id = ro.tm_sys_role_id " + 
			"	LEFT JOIN tm_sys_user us ON us.tm_sys_user_id = ur.tm_sys_user_id  " + 
			"WHERE us.user_name = :userName AND re.res_type = :resType ) ttt " + 
			" ORDER BY ttt.res_level ASC,ttt.parent_res_id asc ",nativeQuery=true)
	public List<TmSysResource> findResourceList(@Param("userName")String userName,@Param("resType")String resType);
}
