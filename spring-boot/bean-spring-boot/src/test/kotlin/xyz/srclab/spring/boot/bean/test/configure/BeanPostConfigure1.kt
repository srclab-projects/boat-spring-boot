package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.configure.BeanPostConfigure
import xyz.srclab.spring.boot.bean.configure.BeanPostContext
import xyz.srclab.spring.boot.bean.test.component.TestService1
import javax.annotation.PostConstruct
import javax.annotation.Resource

@Component
open class BeanPostConfigure1 : BeanPostConfigure {

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
        println("BeanPostConfigure1.init")

        println("testService1: $testService1")
        println("applicationContext: $applicationContext")
        println("environment: $environment")
        println("autowireCapableBeanFactory: $autowireCapableBeanFactory")
    }

    override fun configureAfterInitialization(context: BeanPostContext): Any {
        println("BeanPostConfigure1.configureAfterInitialization, context=${context.name}")
        return context.bean
    }

    override fun configureBeforeInitialization(context: BeanPostContext): Any {
        println("BeanPostConfigure1.configureBeforeInitialization, context=${context.name}")
        return context.bean
    }
}