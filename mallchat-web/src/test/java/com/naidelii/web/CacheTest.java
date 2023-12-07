package com.naidelii.web;

import com.naidelii.chat.user.service.cache.BlackCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

@SpringBootTest
public class CacheTest {

    @Autowired
    private BlackCache blackCache;

    @Test
    public void test(){
        Map<Integer, Set<String>> blackMap = blackCache.getBlackMap();
        System.out.println(blackMap);
        Map<Integer, Set<String>> blackMap1 = blackCache.getBlackMap();
        System.out.println(blackMap1);
    }
}
