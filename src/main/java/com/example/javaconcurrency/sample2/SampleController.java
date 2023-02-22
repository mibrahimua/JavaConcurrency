package com.example.javaconcurrency.sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
public class SampleController {

    @Autowired
    InstanceService instanceService;

    @Autowired
    ThreadPoolService threadPoolService;

    @GetMapping("/testsample")
    String hello() {
        int count = 0;
        while (count < 100) {
            instanceService.runTasks();
            count++;
        }
        return "Hellooo";
    }

    @GetMapping("/check")
    String check() {
        return threadPoolService.getRemainingActiveTask().toString();
    }
}
