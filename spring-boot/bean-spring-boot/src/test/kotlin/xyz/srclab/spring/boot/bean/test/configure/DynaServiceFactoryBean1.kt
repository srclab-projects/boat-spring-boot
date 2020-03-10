package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.FactoryBean
import xyz.srclab.spring.boot.bean.test.component.DynaServiceByFactory1

class DynaServiceFactoryBean1 : FactoryBean<DynaServiceByFactory1> {

    override fun getObject(): DynaServiceByFactory1 {
        println("DynaServiceFactoryBean1.getObject")
        return DynaServiceByFactory1()
    }

    override fun getObjectType(): Class<*>? {
        return DynaServiceByFactory1::class.java
    }
}