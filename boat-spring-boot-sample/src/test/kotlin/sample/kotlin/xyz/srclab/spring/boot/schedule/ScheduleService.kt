package sample.kotlin.xyz.srclab.spring.boot.schedule

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.testng.Assert
import xyz.srclab.common.lang.Current

@Service
@EnableScheduling
open class ScheduleService {

    @Scheduled(cron = "* * * * * *")
    open fun testSchedule() {
        logger.info("Thread: {}", Current.thread.name)
        Assert.assertTrue(Current.thread.name.startsWith("6666"))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduleService::class.java)
    }
}