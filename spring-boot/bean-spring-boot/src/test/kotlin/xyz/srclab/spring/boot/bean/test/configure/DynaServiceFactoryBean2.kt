package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.FactoryBean
import xyz.srclab.spring.boot.bean.test.component.DynaServiceByFactory2

class DynaServiceFactoryBean2 : FactoryBean<DynaServiceByFactory2> {

    override fun getObject(): DynaServiceByFactory2 {
        println("DynaServiceFactoryBean1.getObject")
        return DynaServiceByFactory2()
    }

    override fun getObjectType(): Class<*>? {
        return DynaServiceByFactory2::class.java
    }
}