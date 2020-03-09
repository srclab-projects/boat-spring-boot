package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.Resource

@Component
open class BeanPostProcessor : BeanPostProcessor {

    @Autowired
    private lateinit var testService1: TestService1

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var environment: Environment

    @Resource
    private lateinit var autowireCapableBeanFactory: AutowireCapableBeanFactory

    @PostConstruct
    private fun init() {
        println("BeanPostProcessor.init")

        println("testService1: $testService1")
        println("applicationContext: $applicationContext")
        println("environment: $environment")
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        println("BeanPostProcessor.postProcessBeforeInitialization($beanName:${bean::class.java})")
        if (bean is TestService1) {
            println("testService1.hasService2: " + bean.hasService2())
        }
        if (bean is DynaJavaService1) {
            bean.printInject()

            val lonelyService = LonelyService()
            autowireCapableBeanFactory.autowireBean(lonelyService)
            lonelyService.testLonely()
        }
        if (bean is DynaJavaService2) {
            bean.printInject()
        }
        if (bean is DynaService1) {
            bean.printInject()
        }
        if (bean is DynaService2) {
            bean.printInject()
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        println("BeanPostProcessor.postProcessAfterInitialization($beanName:${bean::class.java})")
        if (bean is TestService1) {
            println("testService1.hasService2: " + bean.hasService2())
        }
        if (bean is DynaJavaService1) {
            bean.printInject()
        }
        if (bean is DynaJavaService2) {
            bean.printInject()
        }
        if (bean is DynaService1) {
            bean.printInject()
        }
        if (bean is DynaService2) {
            bean.printInject()
        }
        return bean
    }
}