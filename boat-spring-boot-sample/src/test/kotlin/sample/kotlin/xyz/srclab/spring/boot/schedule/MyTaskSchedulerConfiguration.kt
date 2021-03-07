package sample.kotlin.xyz.srclab.spring.boot.schedule

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties
import xyz.srclab.spring.boot.schedule.newTaskScheduler

@Configuration
@EnableScheduling
open class MyTaskSchedulerConfiguration {

    @Bean
    open fun taskScheduler(): TaskScheduler {
        val poolProperties = ScheduledPoolProperties()
        poolProperties.threadNamePrefix = "6666"
        return newTaskScheduler(poolProperties)
    }
}