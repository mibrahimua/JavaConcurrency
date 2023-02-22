package com.example.javaconcurrency.sample1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(100);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
                return "this is from future";
            } catch (InterruptedException e) {
                return "error : " + e;
            }
        }, executor);

        System.out.println("this is from present");
//        if(future.isDone()) {
        System.out.println(future.get());
//        }

    }
}
