package com.naidelii.web;

import com.naidelii.data.util.TokenUtils;
import com.naidelii.security.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {


    @Test
    public void createToken() {
        String userId = "1";
        String token = JwtUtils.createToken(userId);
        System.out.println(token);
    }

    @Test
    public void getUserInfo(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjMxMzIxIiwiZXhwIjoxNzAxMDA1Njc4fQ.EqySYsN5ms2GjzrK_dtNO60OSxFJ59HPK8h4-oYJbvs\n";
        String userInfo = JwtUtils.getUserInfo(token);
        System.out.println(userInfo);
    }

    @Test
    public void verifyToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjMxMjMyMSIsImV4cCI6MTcwMTAwNjQ1M30.pfYZS5nMroNvXsJvkgdx9cSTBSTPGkZkcHdVNVOWqrI";
        String userInfo = JwtUtils.getUserInfo(token);
        System.out.println(userInfo);
        System.out.println(JwtUtils.verify(token));
    }

    @Test
    public void tokenUtilsTest(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjMxMzIxIiwiZXhwIjoxNzAxMDA1Njc4fQ.EqySYsN5ms2GjzrK_dtNO60OSxFJ59HPK8h4-oYJbvs\n";
        String userId = TokenUtils.getValidUserId(token);
        System.out.println(userId);
    }
}
