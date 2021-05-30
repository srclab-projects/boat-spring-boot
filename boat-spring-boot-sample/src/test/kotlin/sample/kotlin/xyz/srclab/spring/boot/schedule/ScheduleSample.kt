package sample.kotlin.xyz.srclab.spring.boot.schedule

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.lang.Current.sleep
import xyz.srclab.common.lang.Current.thread
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties
import xyz.srclab.spring.boot.schedule.toTaskScheduler

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, TestTaskSchedulerConfiguration::class, ScheduleService::class]
)
open class ScheduleSample : AbstractTestNGSpringContextTests() {
    @Test
    fun testSchedule() {
        sleep(2000)
    }
}

open class ScheduleService {
    @Scheduled(cron = "* * * * * *")
    fun testSchedule() {
        logger.info("Thread: {}", thread.name)
        Assert.assertTrue(thread.name.startsWith("6666"))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduleService::class.java)
    }
}

@Configuration
@EnableScheduling
open class TestTaskSchedulerConfiguration {
    @Bean
    open fun taskScheduler(): TaskScheduler {
        val poolProperties = ScheduledPoolProperties()
        poolProperties.threadNamePrefix = "6666"
        return poolProperties.toTaskScheduler()
    }
}