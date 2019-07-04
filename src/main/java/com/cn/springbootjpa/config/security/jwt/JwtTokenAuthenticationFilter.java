package com.cn.springbootjpa.config.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * Authenticate requests with header 'Authorization: Bearer jwt-token'.
 *
 */
@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtAuthenticationConfig config;

	public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config) {
		this.config = config;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
			throws ServletException, IOException {
		String token = req.getHeader(config.getHeader());
		if (StringUtils.isAnyBlank(token)) {
			token = req.getParameter(config.getHeader());
		}
		if (!StringUtils.isAnyBlank(token)) {
			String prefix = config.getPrefix();
			if (!StringUtils.isAnyBlank(prefix) && token.startsWith(prefix.trim() + " ")) {
				token = token.replace(prefix.trim() + " ", "");
			}
			try {
				Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token)
						.getBody();
				String username = claims.getSubject();
				@SuppressWarnings("unchecked")
				List<String> authorities = claims.get("authorities", List.class);
				if (username != null) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
					log.info("current loged in user " + username);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (Exception ignore) {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(req, rsp);
	}
}