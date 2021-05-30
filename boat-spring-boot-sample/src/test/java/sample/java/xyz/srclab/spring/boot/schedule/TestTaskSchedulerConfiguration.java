package sample.java.xyz.srclab.spring.boot.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties;
import xyz.srclab.spring.boot.schedule.TaskSchedulers;

@Configuration
@EnableScheduling
public class TestTaskSchedulerConfiguration {

    @Bean
    public TaskScheduler taskScheduler() {
        ScheduledPoolProperties poolProperties = new ScheduledPoolProperties();
        poolProperties.setThreadNamePrefix("6666");
        return TaskSchedulers.newTaskScheduler(poolProperties);
    }
}
