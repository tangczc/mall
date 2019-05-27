package com.rootchen.mall.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: swagger配置
 * @author: LiChen
 * @create: 2019-03-15 14:30
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createBackendRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName("后台")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rootchen.mall.controller.backend"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createProtalRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .groupName("前台")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rootchen.mall.controller.portal"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("慕课网商城后端接口文档")
                .description("2019年3月21日--开发版本")
                .version("1.0")
                .build();
    }

}