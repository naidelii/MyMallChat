package com.naidelii.websocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {
    /**
     * boss线程数量 默认为cpu线程数*2
     */
    private Integer bossExecutor;
    /**
     * worker线程数量 默认为cpu线程数*2
     */
    private Integer workerExecutor;

    /**
     * 连接超时时间 默认为30s
     */
    private Integer timeout = 30000;

    /**
     * 服务器主端口 默认7000
     */
    private Integer port = 7000;

}
