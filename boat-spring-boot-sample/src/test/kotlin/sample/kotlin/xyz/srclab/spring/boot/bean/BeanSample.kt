package sample.kotlin.xyz.srclab.spring.boot.bean

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.DependsOn
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.bean.BeanProperties
import xyz.srclab.spring.boot.bean.BeanRegistry
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, MyBean::class, MyBeanLifecyclePostProcessor::class, MyBeanRegistry::class]
)
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
        log.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.sequence)
        Assert.assertEquals(
            myBeanLifecyclePostProcessor.sequence,
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

@DependsOn("myBeanRegistry")
open class MyBean {

    @Resource
    private lateinit var bean1: String

    @Resource
    private lateinit var bean2: String

    val beanString: String
        get() = bean1 + bean2
}

open class MyBeanRegistry : BeanRegistry() {

    override val registeredSingletons: Map<String, Any> = run {
        val result: MutableMap<String, Any> = HashMap()
        result["bean1"] = "bean1"
        result["bean2"] = "bean2"
        result
    }

    override val registeredBeans: Set<BeanProperties> = run {
        val result: MutableSet<BeanProperties> = HashSet()
        val beanProperties = BeanProperties()
        beanProperties.name = "myBean"
        beanProperties.className = MyBean::class.java.name
        result.add(beanProperties)
        result
    }
}