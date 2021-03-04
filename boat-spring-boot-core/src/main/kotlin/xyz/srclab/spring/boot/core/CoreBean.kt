package xyz.srclab.spring.boot.core

import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import javax.annotation.PostConstruct
import javax.annotation.Resource

open class CoreBean {

    @Resource
    private lateinit var coreBeanProperties: CoreBeanProperties

    @Resource
    private lateinit var applicationContext: ApplicationContext

    @Resource
    private lateinit var listableBeanFactory: ListableBeanFactory

    @Resource
    private lateinit var configurableApplicationContext: ConfigurableApplicationContext

    @Resource
    private lateinit var defaultListableBeanFactory: DefaultListableBeanFactory

    @Resource
    private lateinit var configurableListableBeanFactory: ConfigurableListableBeanFactory

    //@Resource
    //private lateinit var beanDefinitionRegistry: BeanDefinitionRegistry

    @Resource
    private lateinit var haha: String

    @PostConstruct
    private fun init() {
        println(">>>>>>>>>>> Core Bean Init...")
        println(">>>>>>>>>>> applicationContext: $applicationContext")
        println(">>>>>>>>>>> listableBeanFactory: $listableBeanFactory")
        println(">>>>>>>>>>> configurableApplicationContext: $configurableApplicationContext")
        println(">>>>>>>>>>> defaultListableBeanFactory: $defaultListableBeanFactory")
        println(">>>>>>>>>>> configurableListableBeanFactory: $configurableListableBeanFactory")
        //println(">>>>>>>>>>> beanDefinitionRegistry: $beanDefinitionRegistry")

        //val bean = "hahaha"
        //defaultListableBeanFactory.registerSingleton("haha", bean)
        //defaultListableBeanFactory.autowireBean(bean)

        println(">>>>>>>>>>> haha: $haha")
    }

    open fun getCoreProperty(): String? {
        return coreBeanProperties.coreProperty
    }
}