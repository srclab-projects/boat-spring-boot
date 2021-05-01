package test.xyz.srclab.spring.boot.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.srclab.spring.boot.task.TaskDelegate;
import xyz.srclab.spring.boot.task.TaskExecutors;
import xyz.srclab.spring.boot.task.ThreadPoolProperties;

@Configuration
@EnableAsync
public class TestTaskExecutorConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TestTaskExecutorConfiguration.class);

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolProperties poolProperties = new ThreadPoolProperties();
        poolProperties.setThreadNamePrefix("6666");
        return TaskExecutors.newTaskExecutor(poolProperties, (TaskDelegate) (executor, task) -> {
            long l1 = Thread.currentThread().getId();
            executor.execute(() -> {
                long l2 = Thread.currentThread().getId();
                logger.info("thread l1: {}, thread l2: {}", l1, l2);
                task.run();
            });
        });
    }
}
