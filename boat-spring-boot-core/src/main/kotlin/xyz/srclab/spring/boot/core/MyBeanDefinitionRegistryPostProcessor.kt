package xyz.srclab.spring.boot.core

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

/**
 * @author sunqian
 */
open class MyBeanDefinitionRegistryPostProcessor : BeanDefinitionRegistryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        println(">>>>>>postProcessBeanFactory")
        beanFactory.registerSingleton(
            "haha",
            "hahaha777"
//            BeanDefinitionBuilder
//                .genericBeanDefinition(String::class.java)
//                .addConstructorArgValue("hahaha666")
//                .beanDefinition
        )
    }

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        println(">>>>>>postProcessBeanDefinitionRegistry")
        for (beanDefinitionName in registry.beanDefinitionNames) {
            val definition = registry.getBeanDefinition(beanDefinitionName)
            //println("bean: $beanDefinitionName, type: ${definition.beanClassName}")
            if (definition.beanClassName === null) {
                println("bean: $beanDefinitionName, type: ${definition.beanClassName}")
            }
        }

        registry.registerBeanDefinition(
            "xBean",
            BeanDefinitionBuilder
                .genericBeanDefinition(XBean::class.java)
                .beanDefinition
        )
    }
}