package com.cn.springbootjpa.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cn.springbootjpa.config.security.filter.JWTAuthenticationFilter;
import com.cn.springbootjpa.config.security.filter.JWTAuthorizationFilter;
import com.cn.springbootjpa.config.security.utils.SecurityCheck;

/**
 * Created by echisan on 2018/6/23
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("jpaUserDetailsService")
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.cors().and().headers().frameOptions().sameOrigin().and().csrf().disable().logout().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().anonymous()
				.and().exceptionHandling().authenticationEntryPoint((req, rsp, e) -> {
					rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage());
				}).and()
				// token过滤器
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager())).authorizeRequests()
				// 放行swagger2相关资源
				.antMatchers("/webjars/**", "/resources/**", "/swagger-ui.html", "/swagger-resources/**","/v2/api-docs").permitAll()
				// 对于登陆和密码修改两个请求以及异常请求放行
				.antMatchers("/api/login").permitAll().antMatchers("/error").permitAll()
				.antMatchers("/user/updatepwdByUsername").permitAll()
				// 其他请求都匹配权限 securityCheck.check方法
				.antMatchers("/**").access("@securityCheck.check(authentication,request)").anyRequest().authenticated();
		/**
		 * .anonymous() 允许匿名用户访问 .permitAll() 无条件允许访问
		 */
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	/**
	 * 权限校验类
	 * @return
	 */
	@Bean
	public SecurityCheck securityCheck() {
		return new SecurityCheck();
	}

}
