package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.configure.BeanConfigure
import xyz.srclab.spring.boot.bean.configure.BeanDefinitionContext

@Component
open class BeanConfigure2 : BeanConfigure {

    override fun configureBeanDefinition(context: BeanDefinitionContext) {
        println("BeanConfigure2.configureBeanDefinition")
    }
}