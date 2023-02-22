package com.example.javaconcurrency.sample2;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ThreadPoolService {
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
    private ExecutorService vipExecutor;

    public ExecutorService getExecutor() {
        return executor;
    }

    public Integer getRemainingQueuePool() {
        return threadPoolExecutor.getQueue().size();
    }

    public Integer getRemainingActiveTask() {
        return threadPoolExecutor.getActiveCount();
    }

    public ExecutorService getVipExecutor() {
        if (vipExecutor == null) {
            vipExecutor = Executors.newFixedThreadPool(3);
        }
        return vipExecutor;
    }

    public Integer getRemainingVipQueuePool() {
        ThreadPoolExecutor vipThreadPoolExecutor = (ThreadPoolExecutor) vipExecutor;
        return vipThreadPoolExecutor.getQueue().size();
    }

    public Integer getRemainingVipActiveTask() {
        ThreadPoolExecutor vipThreadPoolExecutor = (ThreadPoolExecutor) vipExecutor;
        return vipThreadPoolExecutor.getActiveCount();
    }

}
