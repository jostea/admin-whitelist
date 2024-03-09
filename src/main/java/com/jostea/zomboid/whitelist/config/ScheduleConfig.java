package com.jostea.zomboid.whitelist.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class ScheduleConfig {

    public static final String ASYNC_TASK_EXECUTOR = "asyncTaskExecutor";

    private final WhitelistProperties whitelistProperties;

    @Bean(name = ASYNC_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        final WhitelistProperties.ThreadPool worker = whitelistProperties.getAsyncWorker();

        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(worker.getMinPoolSize());
        taskExecutor.setMaxPoolSize(worker.getMaxPoolSize());
        taskExecutor.setQueueCapacity(worker.getQueueLength());
        taskExecutor.setThreadNamePrefix(worker.getThreadPoolPrefixName());
        taskExecutor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor executor) -> log.error("Unable to schedule task"));

        return taskExecutor;
    }
}
