package com.naidelii.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

    /**
     * 排除资源
     */
    private String excludes;

    /**
     * 放行的请求
     */
    private String notMatch;

}
