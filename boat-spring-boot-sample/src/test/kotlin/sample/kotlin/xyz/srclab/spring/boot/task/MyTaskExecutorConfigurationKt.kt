package sample.kotlin.xyz.srclab.spring.boot.task

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import xyz.srclab.spring.boot.task.TaskPoolProperties
import xyz.srclab.spring.boot.task.toTaskExecutor

@Configuration
@EnableAsync
open class MyTaskExecutorConfigurationKt {

    @Bean
    open fun taskExecutor(): TaskExecutor {
        val poolProperties = TaskPoolProperties()
        poolProperties.threadNamePrefix = "6666"
        return poolProperties.toTaskExecutor()
    }
}