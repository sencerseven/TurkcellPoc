package com.turkcell.poc.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class AsyncConfig {

  @Value("${taskExecutor.threadSize}")
  private Integer threadSize;

  @Value("${taskExecutor.queueCapacity}")
  private Integer queueCapacity;


  @Bean(name = "taskExecutor")
  public Executor taskExecutor() {
    if (threadSize == null || threadSize <= 0) {
      throw new IllegalArgumentException("Thread size must be greater then zero");
    }

    if (queueCapacity == null || queueCapacity <= 0) {
      throw new IllegalArgumentException("Queue capacity must be greater then zero");
    }

    // We manage thread limits
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(threadSize);
    taskExecutor.setMaxPoolSize(threadSize);
    taskExecutor.setQueueCapacity(queueCapacity);
    taskExecutor.setThreadNamePrefix("task-");
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Scope(value = "prototype")
  @Bean("executorCompletionService")
  public ExecutorCompletionService executorCompletionService(
      @Qualifier("taskExecutor") @Autowired Executor taskExecutor
  ) {
    return new ExecutorCompletionService<>(taskExecutor, new LinkedBlockingQueue<>());
  }
}
