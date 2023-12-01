package com.naidelii.web;

import com.naidelii.chat.user.domain.enums.ItemDistributionSceneEnum;
import com.naidelii.chat.user.domain.enums.ItemEnum;
import com.naidelii.chat.user.service.IUserBackpackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class UserBackpackTest {
    @Autowired
    private IUserBackpackService backpackService;

    @Test
    public void distributeItem() {
        String userId = "1724724657689309186";
        backpackService.distributeItem(userId, ItemEnum.REG_TOP10_BADGE.getId(), ItemDistributionSceneEnum.USER_ID.getType(), userId);
    }

    @Test
    public void distributeItemThread() {
        String userId = "1724724657689309186";
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Void>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                Callable<Void> task = () -> {
                    backpackService.distributeItem(userId, ItemEnum.REG_TOP10_BADGE.getId(), ItemDistributionSceneEnum.USER_ID.getType(), userId);
                    return null;
                };
                Future<Void> future = executorService.submit(task);
                futures.add(future);
            }
            // 等待所有任务完成
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
