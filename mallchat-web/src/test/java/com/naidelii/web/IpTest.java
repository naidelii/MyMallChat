package com.naidelii.web;


import com.naidelii.chat.user.domain.entity.IpInfo;
import com.naidelii.chat.user.service.impl.IpServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IpTest {
    @Autowired
    private IpServiceImpl ipService;

    @Test
    public void getIpTest() {
        IpInfo.IpDetail ipDetailOrNull = ipService.getIpDetailOrNull("127.0.0.1");
        System.out.println(ipDetailOrNull);
    }
}
