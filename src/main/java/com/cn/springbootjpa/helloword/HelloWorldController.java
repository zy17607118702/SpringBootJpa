package com.cn.springbootjpa.helloword;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.master.entity.TmBasPart;
import com.cn.springbootjpa.master.entity.TmBasPartDetail;
import com.cn.springbootjpa.master.entity.TmSysUser;
import com.cn.springbootjpa.util.MD5;

@RestController
public class HelloWorldController {

	/**
	 * 类似与Hibernate SessionFactory 
	 */
	//@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@GetMapping(value="/index")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(value="/adduser")
	public TmSysUser adduser() {
		//1,创建EntityFactory 自动注入
		//2.创建EntityManager
		EntityManager manager = entityManagerFactory.createEntityManager();
		//3.开启事务
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		//4.进行持久化操作
		TmSysUser user = new TmSysUser();
		user.setUserName("smctv");
		user.setUserPwd(MD5.md5("123"));
		user.setRealName("张三");
		user.setSex("1");
		user.setAge(21);
		user.setPhone("12315");
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
	
	@GetMapping("/addpart")
	public TmBasPart addpart() {
		TmBasPart part=new TmBasPart();
		part.setPartNo("ATS031");
		part.setPartNameC("车辆左轮毂");
		part.setPartType(1);
		part.setMarkStatus(true);
		Set<TmBasPartDetail> detail=new HashSet<>();
		TmBasPartDetail detail1=new TmBasPartDetail();
		detail1.setTmBasPartId(part.getId());
		detail1.setTmBasPart(part);
		detail1.setPartdetailCode("AS21");
		detail1.setPartdetailName("制动卡钳");
		detail1.setPartdetailCount(1);
		detail1.setMarkStatus(true);
		TmBasPartDetail detail2=new TmBasPartDetail();
		detail2.setTmBasPartId(part.getId());
		detail2.setTmBasPart(part);
		detail2.setPartdetailCode("AS22");
		detail2.setPartdetailName("铝制保护层");
		detail2.setPartdetailCount(1);
		detail2.setMarkStatus(true);
		detail.add(detail1);
		detail.add(detail2);
		part.setTmBasPartDetail(detail);
		EntityManager manager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(part);;
		transaction.commit();
		//6.关闭EntityManager
		manager.close();
		return part;
	}
}
