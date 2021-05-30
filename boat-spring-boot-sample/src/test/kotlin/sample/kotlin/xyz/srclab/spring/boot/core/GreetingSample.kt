package sample.kotlin.xyz.srclab.spring.boot.core

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.core.StartGreeting

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, GreetingSample::class]
)
class GreetingSample : AbstractTestNGSpringContextTests(), StartGreeting {

    @Test
    fun testAutoConfigure() {
    }

    override fun doGreeting() {
        log.info(">>>>>>>>>>>>>>>>>> This is sample greeting!")
    }

    companion object {
        private val log = LoggerFactory.getLogger(GreetingSample::class.java)
    }
}