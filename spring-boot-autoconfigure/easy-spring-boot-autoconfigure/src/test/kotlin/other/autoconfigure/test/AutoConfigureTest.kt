package other.autoconfigure.test

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import javax.annotation.Resource

@SpringBootTest(classes = [Config::class])
class AutoConfigureTest : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var testBeanConfigure: TestBeanConfigure

    @Resource
    private lateinit var testBeanPostConfigure: TestBeanPostConfigure

    @Test
    fun testAutoConfigure() {
        testBeanConfigure.printMarkInfo()
        Assert.assertEquals(
            testBeanConfigure.getActualMark("TestBeanConfigure.configureBeanDefinition"),
            testBeanConfigure.generateMark("TestBeanConfigure.configureBeanDefinition")
        )
        testBeanPostConfigure.printMarkInfo()
        Assert.assertEquals(
            testBeanPostConfigure.getActualMark("TestBeanPostConfigure.configureBeforeInitialization"),
            testBeanPostConfigure.generateMark("TestBeanPostConfigure.configureBeforeInitialization")
        )
    }
}