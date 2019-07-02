package com.cn.springbootjpa.util.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 描述
 *
 * @author zhangyang
 * @date 2018/6/9 11:46
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 自行修改为自己的包路径
				.apis(RequestHandlerSelectors.basePackage("com.cn.springbootjpa")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("swagger-api文档").description("swagger接入教程")
				// 服务条款网址
				.termsOfServiceUrl("https://blog.csdn.net/Zz1ta1").version("1.0")
				.contact(new Contact("ZY", "https://blog.csdn.net/Zz1ta1", "1579079951@qq.com")).build();
	}
}
