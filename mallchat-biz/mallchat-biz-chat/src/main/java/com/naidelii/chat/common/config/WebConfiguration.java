package com.naidelii.chat.common.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.naidelii.chat.user.interceptor.RoleInterceptor;
import com.naidelii.security.config.SecurityProperties;
import com.naidelii.security.interceptor.TokenInterceptor;
import com.naidelii.security.interceptor.UserInfoInterceptor;
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
    private final RoleInterceptor roleInterceptor;

    /**
     * 添加token拦截器（作用时机DispatcherServlet之后），用来检查是否登录
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getExcludes(), ","))
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getNotMatch(), ","));
        registry.addInterceptor(new UserInfoInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getExcludes(), ","))
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityProperties.getNotMatch(), ","));
        registry.addInterceptor(roleInterceptor);
    }
}
