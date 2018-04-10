package com.cnbmtech;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cnbmtech.system.interceptor.AdminInterceptor;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
	
	@Bean
	public AdminInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration ir = registry.addInterceptor(adminInterceptor());
		// 配置拦截的路径
		ir.addPathPatterns("/**");
		// 配置不拦截的路径
		ir.excludePathPatterns("/a/login");
		ir.excludePathPatterns("/a/logout");
		ir.excludePathPatterns("/a/noPermission");
		ir.excludePathPatterns("/template/con_add/*/*");
		ir.excludePathPatterns("/template/con_add_save");
		ir.excludePathPatterns("/template/channel/**");
		ir.excludePathPatterns("/template/qrCode/**");
		ir.excludePathPatterns("/vistor/*/*");
		// 还可以在这里注册其它的拦截器
		
	}
	
}