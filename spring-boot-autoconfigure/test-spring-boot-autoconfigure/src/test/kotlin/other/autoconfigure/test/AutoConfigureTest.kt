package other.autoconfigure.test

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@SpringBootTest(classes = [Config::class])
class AutoConfigureTest : AbstractTestNGSpringContextTests() {

    @Test
    fun testAutoConfigure() {
    }
}