package com.sumit.notificationservice.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configurable
public class AppConfiguration {
    @Bean(name = "asyncConfiguration")
    public Executor getAsyncThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("asyncConfiguration");
        executor.initialize();
        return executor;
    }
}
