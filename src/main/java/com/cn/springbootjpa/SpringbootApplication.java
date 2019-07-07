package com.cn.springbootjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

@EnableJpaRepositories("com.cn.springbootjpa.*") // JPA扫描该包路径下的Repositorie
@EntityScan("com.cn.springbootjpa.*") // 扫描实体类
@EnableCaching //开启缓存注解
@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	
	@Bean
	public OpenSessionInViewFilter sessionFilter() {
		return new OpenSessionInViewFilter();
	}
}
