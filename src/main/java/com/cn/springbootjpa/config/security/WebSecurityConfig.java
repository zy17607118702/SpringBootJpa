package com.cn.springbootjpa.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cn.springbootjpa.config.security.jwt.JwtAuthenticationConfig;
import com.cn.springbootjpa.config.security.jwt.JwtUsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationConfig config;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// @formatter:off
        httpSecurity
	        .csrf().disable()
	        .logout().disable()
	        .formLogin().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	            .anonymous()
	        .and()
	            .exceptionHandling().authenticationEntryPoint(
	                    (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	        .and()
	            .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
	                    UsernamePasswordAuthenticationFilter.class)
	        .authorizeRequests()
//	            .antMatchers(config.getUrl()).permitAll()
	            .anyRequest().authenticated();
        // @formatter:off
    }

  @Bean
  public JwtAuthenticationConfig jwtConfig() {
    return new JwtAuthenticationConfig();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
