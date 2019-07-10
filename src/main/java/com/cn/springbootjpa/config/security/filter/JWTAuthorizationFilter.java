package com.cn.springbootjpa.config.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cn.springbootjpa.config.security.utils.JwtTokenUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by echisan on 2018/6/23
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	//前端web token的key值
	private static final String WEB_TOKEN="Admin-Token";
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //获取token信息 检查token似乎否超时
        try {
        	String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        	Claims body = Jwts.parser().setSigningKey(JwtTokenUtils.SECRET).parseClaimsJws(token).getBody();
        	String username=body.getSubject();
			@SuppressWarnings("unchecked")
			List<String> roles = body.get(JwtTokenUtils.ROLE_CLAIMS,List.class);
            //设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token,username,roles));
            super.doFilterInternal(request, response, chain);
        }catch(Exception ex) {
        	/**
        	 * 存在bug 当前端token过期时后端报异常前端不跳转到登录页的情况
        	 * 解决：1此时token检查 删除前端存储token的cookie
        	 * 2.返回特殊的响应请求50008
        	 * 3.前端处理到50008 跳转到登陆页面
        	 * add by 0710 15:00 
        	 */
        	//
        	Cookie cookie=new Cookie(WEB_TOKEN," ");
        	cookie.setMaxAge(0);
        	response.setStatus(50008);
        	return;
        }
      
    }

    //创建一个新的token
    private UsernamePasswordAuthenticationToken getAuthentication(String token,String username,List<String> roles) {
        if (username != null){
        	List<SimpleGrantedAuthority> collect = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username, null,collect);
        }
        return null;
    }
}
