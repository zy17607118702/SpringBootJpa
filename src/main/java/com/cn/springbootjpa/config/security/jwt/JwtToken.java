package com.cn.springbootjpa.config.security.jwt;

import java.util.Set;

import lombok.Data;

@Data
public class JwtToken {
	private boolean admin = false;
	private Set<String> roles;
	private String token;
	private String username;

	public JwtToken() {
	}

	public JwtToken(String username, String token, Set<String> set) {
		this.username = username;
		this.token = token;
		this.roles = set;
	}
}