package com.cn.springbootjpa.config.security.jwt;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Authenticate the request to url /login by POST with json body '{ username,
 * password }'. If successful, response the client with header 'Authorization:
 * Bearer jwt-token'.
 *
 */
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtAuthenticationConfig config;
    private final ObjectMapper mapper;

    public JwtUsernamePasswordAuthenticationFilter(JwtAuthenticationConfig config, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(config.getUrl(), "POST"));
        setAuthenticationManager(authManager);
        this.config = config;
        this.mapper = new ObjectMapper();
        setAuthenticationFailureHandler((req, res, e) -> {
            res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
        });
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse rsp, FilterChain chain,
            Authentication auth) {
        Instant now = Instant.now();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder().setSubject(auth.getName()).claim("authorities", roles).setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes()).compact();
        rsp.addHeader(config.getHeader(),
                (StringUtils.isAnyBlank(config.getPrefix()) ? "" : config.getPrefix() + " ") + token);
        rsp.addHeader("Content-Type", "application/json");
        try {
            ServletOutputStream os = rsp.getOutputStream();
            mapper.writeValue(os, new JwtToken(auth.getName(), token));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse rsp)
            throws AuthenticationException, IOException {
        JwtUser u = mapper.readValue(req.getInputStream(), JwtUser.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u,
                u.getPassword());
        authentication.setDetails(new WebAuthenticationDetails(req));
        // add default domain.
        if (StringUtils.isAnyBlank(u.getDomain())) {
            u.setDomain("local");
        }
        return getAuthenticationManager().authenticate(authentication);
    }

}
