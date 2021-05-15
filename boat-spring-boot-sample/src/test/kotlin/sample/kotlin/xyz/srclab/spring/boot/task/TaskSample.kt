package sample.kotlin.xyz.srclab.spring.boot.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test
import xyz.srclab.common.lang.Current
import javax.annotation.Resource

@SpringBootTest(classes = [Starter::class])
open class TaskSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var asyncService: AsyncService

    @Test
    open fun testTask() {
        asyncService.testAsync()
        Current.sleep(1000)
    }
}

@SpringBootApplication
open class Starter