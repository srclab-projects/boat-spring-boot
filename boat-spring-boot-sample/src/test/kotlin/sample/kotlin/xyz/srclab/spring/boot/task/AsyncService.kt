package sample.kotlin.xyz.srclab.spring.boot.task

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.testng.Assert
import xyz.srclab.common.lang.Current

@Service
open class AsyncService {

    @Async
    open fun testAsync() {
        logger.info("Thread: {}", Current.thread.name)
        Assert.assertTrue(Current.thread.name.startsWith("666"))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AsyncService::class.java)
    }
}