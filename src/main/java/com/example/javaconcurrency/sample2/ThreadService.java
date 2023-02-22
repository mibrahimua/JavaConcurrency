package com.example.javaconcurrency.sample2;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ThreadService {
    int countTask = 0;

    String taskId = UUID.randomUUID().toString();

    void counter() {
        try {
            while (countTask < 10) {
                CompletableFuture.runAsync(() -> {
                    try {
                        int randomDelay = new Random().nextInt(2000 - 500 + 500) + 500;
                        Thread.sleep(randomDelay);
                        System.out.println(Thread.currentThread().getName() + " : Task " + countTask + " with task id : " + taskId);
                        countTask++;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
