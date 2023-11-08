package com.naidelii.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author naidelii
 */
@SpringBootApplication(scanBasePackages = {"com.naidelii"})
public class MallChatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallChatServerApplication.class, args);
    }
}
