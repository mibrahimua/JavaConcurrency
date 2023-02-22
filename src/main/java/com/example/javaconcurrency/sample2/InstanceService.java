package com.example.javaconcurrency.sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InstanceService {
    @Autowired
    ThreadPoolService threadPoolService;

    void runTasks() {
        CompletableFuture.runAsync(() -> {
            ThreadService threadService = new ThreadService();
            threadService.counter();
        }, threadPoolService.getExecutor()).thenRun(() -> {
            System.out.println("Remanining task :" + threadPoolService.getRemainingQueuePool());
        });
        if (threadPoolService.getRemainingQueuePool() == 0) {
            System.out.println("all done");
        }
    }
}
