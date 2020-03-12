package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.stereotype.Component
import org.testng.Assert
import xyz.srclab.spring.boot.bean.configure.BeanConfigure
import xyz.srclab.spring.boot.bean.configure.BeanDefinitionContext
import xyz.srclab.spring.boot.bean.test.component.DynaService1
import xyz.srclab.spring.boot.bean.test.component.DynaService2
import xyz.srclab.spring.boot.bean.test.component.DynaServiceByFactory1
import xyz.srclab.spring.boot.bean.test.component.DynaServiceByFactory2

@Component
open class BeanConfigure1 : BeanConfigure {

    override fun configureBeanDefinition(context: BeanDefinitionContext) {
        println("BeanConfigure1.configureBeanDefinition")

        Assert.assertEquals(context.environment.getProperty("test"), "Hello, Easy-Spring")

        val registry = context.registry
        registry.registerBeanDefinition(
            "dynaService1",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaService1::class.java)
                .beanDefinition
        )
        registry.registerBeanDefinition(
            "dynaService2",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaService2::class.java)
                .beanDefinition
        )
        registry.registerBeanDefinition(
            "dynaServiceByFactory1",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaServiceByFactory1::class.java)
                .beanDefinition
        )
        registry.registerBeanDefinition(
            "dynaServiceByFactory2",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaServiceByFactory2::class.java)
                .beanDefinition
        )
        registry.registerBeanDefinition(
            "dynaServiceFactoryBean1",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaServiceFactoryBean1::class.java)
                .beanDefinition
        )
        registry.registerBeanDefinition(
            "dynaServiceFactoryBean2",
            BeanDefinitionBuilder
                .genericBeanDefinition(DynaServiceFactoryBean2::class.java)
                .beanDefinition
        )
    }
}