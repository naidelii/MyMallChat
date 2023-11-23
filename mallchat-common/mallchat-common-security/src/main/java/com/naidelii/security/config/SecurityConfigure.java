package com.naidelii.security.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 */
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
@ToString
@Component
public class SecurityConfigure {

    /**
     * 排除资源
     */
    private String excludes;

    /**
     * 放行的请求
     */
    private String notMatch;

}
