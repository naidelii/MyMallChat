package com.naidelii.security.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.text.CharSequenceUtil;
import com.naidelii.base.constant.enums.ResultEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author naidelii
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class SaTokenConfiguration implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;


    /**
     * 添加sa-token拦截器（作用时机DispatcherServlet之后），用来检查是否登录
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 注册sa-token的过滤器（作用时机DispatcherServlet之前）
     *
     * @return SaServletFilter
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由
                .addInclude("/**")
                // 指定放行路由
                .addExclude(CharSequenceUtil.splitToArray(securityProperties.getExcludes(), ","))
                // 全局认证函数
                .setAuth(obj -> {
                    // 登录校验
                    SaRouter.match("/**")
                            .notMatch(CharSequenceUtil.splitToArray(securityProperties.getNotMatch(), ","))
                            .check(r -> StpUtil.checkLogin());
                })
                // 异常处理函数
                .setError(e -> {
                    // 未登录
                    if (e instanceof NotLoginException) {
                        return SaResult.error(e.getMessage()).setCode(ResultEnum.UNAUTHORIZED.getCode());
                    } else {
                        // 没有权限
                        return SaResult.error(e.getMessage()).setCode(ResultEnum.FORBIDDEN.getCode());
                    }
                })
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

}
