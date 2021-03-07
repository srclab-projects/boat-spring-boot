package sample.kotlin.xyz.srclab.spring.boot.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test
import xyz.srclab.common.base.Current.sleep
import javax.annotation.Resource

@SpringBootTest(classes = [Starter::class])
open class TaskSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var asyncService: AsyncService

    @Test
    open fun testTask() {
        asyncService.testAsync()
        sleep(1000)
    }
}

@SpringBootApplication
open class Starter