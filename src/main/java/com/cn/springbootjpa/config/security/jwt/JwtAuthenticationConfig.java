package com.cn.springbootjpa.config.security.jwt;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

/**
 * Config JWT. Only one property 'security.jwt.secret' is mandatory.
 *
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

	@Value("${security.jwt.expiration:#{24*60*60}}")
	private int expiration; // default 24 hours

	@Value("${security.jwt.header:Authorization}")
	private String header;

	@Value("${security.jwt.prefix:}")
	private String prefix;

	@Value("${security.jwt.secret}")
	private String secret;

	@Value("${security.jwt.url:/api/login}")
	private String url;
}
