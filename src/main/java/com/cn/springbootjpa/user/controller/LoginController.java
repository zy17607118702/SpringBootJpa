package com.cn.springbootjpa.user.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

@RestController
@RequestMapping(value = "login")
public class LoginController {
	@Autowired
	private TmSysUserBo tmSysUserBo;
	
	@GetMapping("user")
	public String checkPassword(@RequestParam("username") String userName,@RequestParam("pwd") String pwd) {
		String password=DigestUtils.md5Hex(pwd);
		System.out.println(password);
		TmSysUser user = tmSysUserBo.checkPassword(userName,password );
		if(user==null || user.getId()==0) {
			return "fail";
		}
		return "success";
	}
}
