package sample.kotlin.xyz.srclab.spring.boot.core

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.DependsOn
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import javax.annotation.Resource

@SpringBootTest(classes = [Starter::class])
//@ContextConfiguration(classes = [TestStarter::class])
@DependsOn("myBeanRegistryKt")
open class BeanSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var myBeanLifecyclePostProcessor: MyBeanLifecyclePostProcessor

    @Resource
    private lateinit var bean1: String

    @Resource
    private lateinit var bean2: String

    @Resource
    private lateinit var myBean: MyBean

    @Test
    fun testBeanPostProcessor() {
        log.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.getSequence())
        Assert.assertEquals(
            myBeanLifecyclePostProcessor.getSequence(),
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
        log.info("bean1: {}", bean1)
        Assert.assertEquals(bean1, "bean1")
        log.info("bean2: {}", bean2)
        Assert.assertEquals(bean2, "bean2")
        log.info("myBean: {}", myBean.beanString)
        Assert.assertEquals(myBean.beanString, bean1 + bean2)
    }

    companion object {
        private val log = LoggerFactory.getLogger(BeanSample::class.java)
    }
}

@SpringBootApplication
open class Starter