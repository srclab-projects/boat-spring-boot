package xyz.srclab.spring.boot.core

import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * @author sunqian
 */
open class XBean {

    @Resource
    private lateinit var coreBean: CoreBean

    @PostConstruct
    private fun init() {
        println("xxxxxxxxxx: $coreBean")
    }
}