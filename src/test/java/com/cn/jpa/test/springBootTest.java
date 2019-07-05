package com.cn.jpa.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cn.springbootjpa.SpringbootApplication;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

@RunWith(SpringRunner.class)   
@SpringBootTest(classes={SpringbootApplication.class})
public class springBootTest {

	@Autowired
	private TmSysUserBo tmSysUserBo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Test
	public void changePwd() {
		TmSysUser user = tmSysUserBo.findByUsername("smcet");
		String encode = bCryptPasswordEncoder.encode("123");
		tmSysUserBo.updatePwd(user.getUserName(), encode);
	}
}
