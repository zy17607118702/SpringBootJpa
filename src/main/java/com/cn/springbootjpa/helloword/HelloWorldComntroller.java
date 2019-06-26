package com.cn.springbootjpa.helloword;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.master.entity.TmSysUser;
import com.cn.springbootjpa.util.MD5;

@RestController
public class HelloWorldComntroller {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@GetMapping(value="/index")
	public String HelloWorld() {
		return "Hello World";
	}
	
	@GetMapping(value="/getuser")
	public TmSysUser getuser() {
		//1,创建EntityFactory 自动注入
		//2.创建EntityManager
		EntityManager manager = entityManagerFactory.createEntityManager();
		//3.开启事务
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		//4.进行持久化操作
		TmSysUser user = new TmSysUser();
		user.setUserName("shmct");
		user.setUserPwd(MD5.md5("1234"));
		user.setRealName("张三");
		user.setSex("2");
		user.setAge(27);
		user.setPhone("12311");
		user.setEmail("1579079951@qq.com");
		user.setLocked(false);
		user.setMarkStatus(true);
		manager.persist(user);
		//5.提交事务
		transaction.commit();
		//6.关闭EntityManager
		manager.close();
		//7.关闭EntityFactory
//		entityManagerFactory.close();
		return user;
	}
	
}
