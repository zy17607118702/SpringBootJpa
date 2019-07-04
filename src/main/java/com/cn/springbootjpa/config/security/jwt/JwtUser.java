package com.cn.springbootjpa.config.security.jwt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import lombok.Data;

/**
 */
@Data
public class JwtUser implements Serializable {

	private static final long serialVersionUID = -8500813685988190279L;
	private Collection<String> roles;
	private boolean enabled;
	private Long id;
	private String password;
	private String username;
	private String domain;
	private String lang = Locale.CHINA.toString();

	public JwtUser() {
		super();
	}

	public JwtUser(String username, String password, String domain, String lang) {
		this.username = username;
		this.password = password;
		this.domain = domain;
		this.lang = lang;
	}

	public String toString() {
		return this.username;
	}

}
