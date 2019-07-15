/**
 * @Description: TODO
 * @author zhengangwu
 */

package com.cnbmtech.cdwpcore.aaa.config;

import springfox.documentation.builders.PathSelectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cnbmtech.cdwpcore.aaa.utils.IPUtil;
import com.google.common.base.Predicate;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import({ springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class,
		springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class })
public class SwaggerConfig {
	@Value("${spring.application.name}")
	private String appname;
	@Value("${termsOfServiceUrl}")
	private String termsOfServiceUrl;
	@Value("${version}")
	private String version;

	@Bean
	public Docket userApi() {
		final String appname1 = appname.replaceAll("localhost", IPUtil.getServerAddr());

		return new Docket(DocumentationType.SWAGGER_2).groupName(appname1).apiInfo(apiInfo()).select()
				.paths(userPaths()).build().ignoredParameterTypes(ApiIgnore.class).enableUrlTemplating(false);
	}

	Predicate<String> userPaths() {
		return PathSelectors.regex(".*");
	}

	ApiInfo apiInfo() {
		final String termsOfServiceUrl1 = termsOfServiceUrl.replaceAll("localhost", IPUtil.getServerAddr());
		final String appname1 = appname.replaceAll("localhost", IPUtil.getServerAddr());
		return new ApiInfoBuilder().title(appname1).description(appname1).termsOfServiceUrl(termsOfServiceUrl1)
				.version(version).build();
	}
}
