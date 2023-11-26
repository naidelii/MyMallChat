package com.naidelii.web;

import com.naidelii.chat.user.service.ILoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginTest {
    @Autowired
    private ILoginService loginService;

    @Test
    public void login() {
        String token = loginService.scanQRCodeLogin("123");
        System.out.println(token);
    }
}
