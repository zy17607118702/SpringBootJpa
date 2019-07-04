/**********************************************************************
 * FILE : SecurityUtils.java
 * CREATE DATE : ${date}
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | ${date} |  ${user}  |    创建草稿版本
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.cn.springbootjpa.config.security.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

/**
 * 
 * 安全操作的工具类，用于获取已认证用户信息。
 * 
 * @author LiangShenFan
 */
public class SecurityUtils {
	private static final String DEFAULT_NO_USERNAME = "00000";

	/**
	 * 用于保存系统中用户列表信息,对应的username和userID
	 */
	final public static Map<String, Integer> userid_map = new HashMap<String, Integer>();

	/**
	 * TmSysUserBO 管理者，用于对用户的数据信息进行操作
	 */
	@Autowired
	private static  TmSysUserBo userBo;

	/**
	 * 从已登录的用户中获取登录用户名对应的用户ID。
	 * 
	 * @param username 用户登录名
	 * @return 已登录用户的用户ID
	 */
	// public final static Long getLoginIdByUsername(String username) {
	// return userid_map.get(username);
	// }
	/**
	 * 
	 * 获取当前登录的用户ID
	 * 
	 * @return 返回当前登录的用户ID
	 */
	public final static Integer getLoginId() {
		String username = getLoginUser();
		if (userid_map.containsKey(username)) {
			return userid_map.get(username);
		} else {
			if (!DEFAULT_NO_USERNAME.equals(username) && StringUtils.isNotBlank(username)) {
				TmSysUser user = userBo.findByUsername(username);
				if (user != null) {
					Integer id = user.getId();
					userid_map.put(username, id);
					return id;
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * 获取当前登录用户的username
	 * 
	 * @return 返回当前登录用户的username
	 */
	public final static String getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
			String username = ((UsernamePasswordAuthenticationToken) authentication).getName();
			return username;
		}
		return DEFAULT_NO_USERNAME;
	}

}
