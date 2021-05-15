package sample.kotlin.xyz.srclab.spring.boot.schedule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test
import xyz.srclab.common.lang.Current

@SpringBootTest(classes = [Starter::class])
open class ScheduleSample : AbstractTestNGSpringContextTests() {

    @Test
    open fun testSchedule() {
        Current.sleep(2000)
    }
}

@SpringBootApplication
open class Starter