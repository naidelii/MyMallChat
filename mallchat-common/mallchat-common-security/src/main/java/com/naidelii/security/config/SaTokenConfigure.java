package com.naidelii.security.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author naidelii
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SaTokenConfigure implements WebMvcConfigurer {

    private final SecurityConfigure securityConfigure;


    /**
     * 添加sa-token拦截器（作用时机DispatcherServlet之后），用来检查是否登录
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(CharSequenceUtil.splitToArray(securityConfigure.getNotMatch(), ","));
    }

    /**
     * 注册sa-token的过滤器（作用时机DispatcherServlet之前）
     *
     * @return SaServletFilter
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由]
                .addInclude("/**")
                // 指定放行路由
                .addExclude(CharSequenceUtil.splitToArray(securityConfigure.getExcludes(), ","))
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> log.info("--------OPTIONS预检请求，不做处理"))
                            .back();
                });
    }


    /**
     * 放行的静态资源
     *
     * @return List<String>
     */
    private List<String> excludeStaticResource() {
        // 放行swagger
        List<String> list = new ArrayList<>();
        list.add("/favicon.ico");
        list.add("/doc.html");
        list.add("/webjars/**");
        list.add("/swagger-resources/**");
        list.add("/v2/api-docs");
        return list;
    }
}
