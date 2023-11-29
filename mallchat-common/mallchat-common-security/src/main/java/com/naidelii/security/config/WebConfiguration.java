package com.naidelii.security.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.naidelii.security.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author naidelii
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * 添加token拦截器（作用时机DispatcherServlet之后），用来检查是否登录
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getExcludes(), ","))
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getNotMatch(), ","));
    }
}
