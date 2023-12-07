package com.naidelii.chat.user.service.cache;

import com.naidelii.chat.user.dao.BlackDao;
import com.naidelii.chat.user.domain.entity.Black;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author naidelii
 * 黑名单缓存
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BlackCache {
    private final BlackDao blackDao;


    @Cacheable(cacheNames = "black", key = "'blackList'")
    public Map<Integer, Set<String>> getBlackMap() {
        // 1.获取所有的黑名单
        List<Black> list = blackDao.list();
        // 1.1根据类型进行分组
        Map<Integer, List<Black>> collect = list
                .stream()
                .collect(Collectors.groupingBy(Black::getType));
        // 1.2创建返回的map
        Map<Integer, Set<String>> result = new HashMap<>(5);
        // 2.遍历分组的数据，进行组装
        collect.forEach((type, value) -> result.put(type, value.stream().map(Black::getTarget).collect(Collectors.toSet())));
        return result;
    }

    @CacheEvict(cacheNames = "black", key = "'blackList'")
    public void emptyBlackMap() {
        log.info("=========emptyBlackMap===========");
    }
}
