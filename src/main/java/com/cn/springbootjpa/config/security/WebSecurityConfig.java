package com.cn.springbootjpa.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cn.springbootjpa.config.security.jwt.JwtAuthenticationConfig;
import com.cn.springbootjpa.config.security.jwt.JwtTokenAuthenticationFilter;
import com.cn.springbootjpa.config.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.cn.springbootjpa.config.security.suc.SucAuthenticationProvider;
import com.cn.springbootjpa.config.security.utils.SecurityCheck;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    // @Autowired
    // private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new SucAuthenticationProvider();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity.headers().frameOptions().sameOrigin()
                .and().csrf().disable().logout().disable().formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().anonymous()
                .and().exceptionHandling().authenticationEntryPoint((req, rsp, e) ->{
                    rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getLocalizedMessage());
                })
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                        JwtTokenAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/*.html").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/**/api-docs/**").permitAll()
                .antMatchers(config.getUrl()).permitAll()
                // Error must be set to permitAll
                .antMatchers("/error").permitAll()
                .antMatchers("/user/updatepwdByUsername").permitAll()
//                .antMatchers("/**").access("@securityCheck.check(authentication,request)")
                .anyRequest().authenticated()
                ;
        // @formatter:off
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider());
        // builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Bean
    public SecurityCheck securityCheck() {
        return new SecurityCheck();
    }
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // auth.userDetailsService(userDetailsService);
    // }
    // @Bean
    // @Override
    // protected UserDetailsService userDetailsService() {
    // return userDetailsService;
    // }
}
