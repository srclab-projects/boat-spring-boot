package sample.kotlin.xyz.srclab.spring.boot.task

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.lang.Current.sleep
import xyz.srclab.common.lang.Current.thread
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.task.TaskDelegate
import xyz.srclab.spring.boot.task.TaskPoolProperties
import xyz.srclab.spring.boot.task.executeWithMdc
import xyz.srclab.spring.boot.task.toTaskExecutor
import java.util.concurrent.Executor
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, TaskExecutorConfiguration::class, AsyncService::class]
)
open class TaskSample : AbstractTestNGSpringContextTests() {
    @Resource
    private lateinit var asyncService: AsyncService

    @Test
    fun testTask() {
        asyncService!!.testAsync()
        sleep(1000)
    }
}

open class AsyncService {
    @Async
    open fun testAsync() {
        logger.info(
            "Thread: {}",
            thread.name
        )
        Assert.assertTrue(thread.name.startsWith("6666"))
        Assert.assertEquals(MDC.get("123"), "123")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AsyncService::class.java)
    }
}

@Configuration
@EnableAsync
open class TaskExecutorConfiguration {
    @Bean
    open fun taskExecutor(): TaskExecutor {
        val poolProperties = TaskPoolProperties()
        poolProperties.threadNamePrefix = "6666"
        return poolProperties.toTaskExecutor(object : TaskDelegate {
            override fun execute(executor: Executor, task: Runnable) {
                val l1 = Thread.currentThread().id
                MDC.put("123", "123")
                executeWithMdc(executor!!, {
                    val l2 = Thread.currentThread().id
                    logger.info("thread l1: {}, thread l2: {}", l1, l2)
                    task.run()
                })
            }
        })
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TaskExecutorConfiguration::class.java)
    }
}