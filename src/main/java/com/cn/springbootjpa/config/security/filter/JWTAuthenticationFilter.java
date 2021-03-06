package com.cn.springbootjpa.config.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cn.springbootjpa.config.security.model.JwtToken;
import com.cn.springbootjpa.config.security.model.LoginUser;
import com.cn.springbootjpa.config.security.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by echisan on 2018/6/23
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Integer> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    	this.mapper = new ObjectMapper();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        boolean isRemember = rememberMe.get()!=null? rememberMe.get()== 1:false;
        String username = authResult.getName();
        List<String> roles = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = JwtTokenUtils.createToken(username, roles, isRemember);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        System.out.println("token="+token);
        response.addHeader("Content-Type", "application/json");
        try {
            ServletOutputStream os = response.getOutputStream();
            mapper.writeValue(os, new JwtToken(username, JwtTokenUtils.TOKEN_PREFIX + token));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
