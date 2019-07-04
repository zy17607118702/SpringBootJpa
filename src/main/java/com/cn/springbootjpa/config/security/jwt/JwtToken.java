package com.cn.springbootjpa.config.security.jwt;

import lombok.Data;

@Data
public class JwtToken {
	private boolean admin = false;
	private String token;
	private String username;

	public JwtToken() {
	}

	public JwtToken(String username, String token) {
		this.username = username;
		this.token = token;
	}
}