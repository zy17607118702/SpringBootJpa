package com.cn.springbootjpa.config.security.utils;

import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cn.springbootjpa.base.common.code.CodeTypeConstants;
import com.cn.springbootjpa.user.bo.TrSysRoleResourceBo;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据授权菜单进行简单的URL严格授权控制。
 * 
 * @author zhangyang
 *
 */
@Slf4j
public class SecurityCheck {

	@Autowired
	private TrSysRoleResourceBo trSysRoleResourceBo;

	private String prefix;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public SecurityCheck() {
	}

	public SecurityCheck(String prefix) {
		this.prefix = prefix;
	}

	// .antMatchers("/**").access("@securityCheck.check(authentication,request)")
	public boolean check(Authentication authentication, HttpServletRequest request) {
		log.info(" ****** request url {}", request.getRequestURL());
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames.hasMoreElements()) {
			log.debug(" ****** request header {}", request.getHeader(headerNames.nextElement()));
		}
		// 如果权限是admin  直接不校验权限
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if(roles.contains(CodeTypeConstants.ROLE_ADMIN)) {
			return true;
		}
		// 某一些请求不需要权限 需要这些不过滤的请求挑选出来
		String[] urls = new String[] { "/ds/**,/user/info/,/user/fullname,/swagger2**,/resource/web/resources" };
		for(String url:urls) {
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
			if(matcher.matches(request)) {
				return true;
			}
		}
		if (authentication.isAuthenticated()) {
			
			List<String> patterns = trSysRoleResourceBo.findRoleResources(roles);
			if (patterns != null && !patterns.isEmpty()) {
				for (String p : patterns) {
					if (!p.contains("**")) {
						p = p + "/**";
					}
					// 添加前缀
					if (!StringUtils.isAnyBlank(prefix)) {
						p = prefix + p;
					}
					AntPathRequestMatcher matcher = new AntPathRequestMatcher(p);
					if (matcher.matches(request)) {
						log.info(" ****** granted access for user {} to url {}.", authentication.getName(),
								request.getRequestURL());
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String acls = "/a/**, /b/**";
		String[] split = acls.split("[, ]");
		for (String s : split) {
			System.out.println(s);
		}
	}
}
