package sample.kotlin.xyz.srclab.spring.boot.task

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import xyz.srclab.spring.boot.task.ThreadPoolProperties
import xyz.srclab.spring.boot.task.newTaskExecutor

@Configuration
@EnableAsync
open class MyTaskExecutorConfigurationKt {

    @Bean
    open fun taskExecutor(): TaskExecutor {
        val poolProperties = ThreadPoolProperties()
        poolProperties.threadNamePrefix = "6666"
        return newTaskExecutor(poolProperties)
    }
}