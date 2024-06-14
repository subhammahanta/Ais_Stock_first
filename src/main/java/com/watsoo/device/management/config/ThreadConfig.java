package com.watsoo.device.management.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {

    @Bean(name = "myExecutor")
    public ExecutorService myExecutor() {
        return Executors.newCachedThreadPool(); // Creates a thread pool that creates new threads as needed.
    }
}