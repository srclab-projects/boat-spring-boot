package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
open class BeanFactoryPostProcessor : BeanFactoryPostProcessor {

//    @Autowired
//    private lateinit var testService1: TestService1

//    @Autowired
//    private lateinit var  applicationContext: ApplicationContext
//
//    @Autowired
//    private lateinit var  environment: Environment

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        println("BeanFactoryPostProcessor.postProcessBeanFactory")

//        println("testService1: $testService1")
//        println("applicationContext: $applicationContext")
//        println("environment: $environment")
    }
}