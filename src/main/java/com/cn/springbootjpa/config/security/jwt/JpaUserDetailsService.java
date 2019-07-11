package com.cn.springbootjpa.config.security.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.config.security.model.JwtUser;
import com.cn.springbootjpa.system.bo.user.TmSysUserBo;
import com.cn.springbootjpa.system.bo.user.TrSysUserRoleBo;
import com.cn.springbootjpa.system.entity.user.TmSysUser;

@Service
@Transactional(readOnly = true)
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private TmSysUserBo service;
    @Autowired
    private TrSysUserRoleBo trSysUserRoleBo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TmSysUser user = service.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username [" + username + "] user not found.");
        }
        List<String> roles = trSysUserRoleBo.findUserRoles(username);
        return new JwtUser(user,roles);
    }
}
