package xyz.srclab.spring.boot.core

import javax.annotation.PostConstruct
import javax.annotation.Resource

open class CoreBean {

    @Resource
    private lateinit var coreBeanProperties: CoreBeanProperties

    @PostConstruct
    private fun init() {
        println(">>>>>>>>>>> Core Bean Init!")
    }

    open fun getCoreProperty(): String? {
        return coreBeanProperties.coreProperty
    }
}