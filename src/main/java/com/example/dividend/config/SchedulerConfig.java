package com.example.dividend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

    int coreCnt = Runtime.getRuntime().availableProcessors();
    threadPool.setPoolSize(coreCnt);
    threadPool.initialize();

    taskRegistrar.setTaskScheduler(threadPool);
  }
}
