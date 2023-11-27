package com.naidelii.data.config;

import com.naidelii.base.constant.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author naidelii
 * 实现AsyncConfigurer接口，替换springboot默认的线程池
 */
@Configuration
@EnableAsync
public class ThreadPoolExecutorConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return mallChatExecutor();
    }

    @Bean(CommonConstants.MALL_CHAT_EXECUTOR)
    public Executor mallChatExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        // 关闭线程池时等待所有正在执行的任务完成
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 核心池大小
        threadPoolExecutor.setCorePoolSize(10);
        // 最大线程数
        threadPoolExecutor.setMaxPoolSize(10);
        // 队列程度
        threadPoolExecutor.setQueueCapacity(200);
        // 配置线程池饱和策略（当任务无法立即被执行时，将会使用调用线程（提交任务的线程）来执行该任务）
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名字前缀
        threadPoolExecutor.setThreadNamePrefix(CommonConstants.MALL_CHAT_EXECUTOR_PREFIX);
        // 初始化配置
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }
}
