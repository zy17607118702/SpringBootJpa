package com.cn.springbootjpa.config.security.suc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.stereotype.Service;

import com.cn.springbootjpa.config.security.jwt.JwtUser;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.bo.TrSysUserRoleBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SucAuthenticationProvider implements AuthenticationProvider {
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Autowired
    private TmSysUserBo userBo;
    @Autowired
    private TrSysUserRoleBo trSysUserRoleBo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtUser user=new JwtUser();
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		user.setUsername(username);
		Object principal = authentication.getPrincipal();
		// Determine username
		String password = (String) authentication.getCredentials();
		user.setPassword(password);
		// suc domain
		String domain = "";
		String lang = Locale.CHINA.toString();
		if (principal instanceof JwtUser) {
			domain = ((JwtUser) principal).getDomain();
			lang = ((JwtUser) principal).getLang();
			user.setDomain(domain);
			user.setLang(lang);
		}
		if (StringUtils.isAnyBlank(domain)) {
			domain = "local";
			user.setDomain(domain);
			user.setLang(lang);
		}

		try {
			TmSysUser sysUser = userBo.checkPassword(username, password);
			if (sysUser!=null) {
				List<String> roles = trSysUserRoleBo.findUserRoles(username);
				user.setRoles(roles);
				return createSuccessAuthentication(principal, authentication, user);
			}
			throw new AuthenticationServiceException("登陆异常 请联系管理员");
		} catch (Exception e) {
			log.error("", e);
			throw new AuthenticationServiceException(e.getLocalizedMessage());
		}
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			JwtUser user) {
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[0]));

		// Ensure we return the original credentials the user supplied,
		// so subsequent attempts are successful even with encoded passwords.
		// Also ensure we return the original getDetails(), so that future
		// authentication events after cache expiry contain the details
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
				authentication.getCredentials(), authoritiesMapper.mapAuthorities(authorityList));
		result.setDetails(authentication.getDetails());
		return result;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
