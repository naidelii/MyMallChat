package com.naidelii.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author naidelii
 * Swagger文档配置
 */
@Configuration
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 请求头
     */
    private final static String AUTHORIZATION = "Authorization";

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 添加摘要信息
     *
     * @return 摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("mallchat接口文档")
                // 描述
                .description("接口文档")
                // 作者信息
                .contact(new Contact("Naidelii", "https://www.naidelii.top/", "naidelii@qq.com"))
                // 版本
                .version("1.0")
                .build();
    }
}
