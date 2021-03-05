package sample.xyz.srclab.spring.boot.core

import org.slf4j.LoggerFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import sample.xyz.srclab.spring.boot.TestStarter
import javax.annotation.Resource

//@SpringBootTest
@ContextConfiguration(classes = [TestStarter::class])
class BeanSampleKt : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var testBeanPostProcessor: TestBeanPostProcessor

    @Resource
    private lateinit var bean1Kt: String

    @Resource
    private lateinit var bean2Kt: String

    @Test
    fun testBeanPostProcessor() {
        log.info("Bean processing sequence: {}", testBeanPostProcessor.sequence)
        Assert.assertEquals(
            testBeanPostProcessor.sequence,
            listOf(
                "postProcessBeanDefinitionRegistry",
                "postProcessBeanFactory",
                "postProcessBeforeInstantiation",
                "postProcessAfterInstantiation",
                "postProcessProperties",
                "postProcessBeforeInitialization",
                "postProcessAfterInitialization"
            )
        )
    }

    @Test
    fun testBeanManager() {
        log.info("bean1Kt: {}", bean1Kt)
        Assert.assertEquals(bean1Kt, "bean1Kt")
        log.info("bean2Kt: {}", bean2Kt)
        Assert.assertEquals(bean2Kt, "bean2Kt")
    }

    companion object {
        private val log = LoggerFactory.getLogger(BeanSample::class.java)
    }
}