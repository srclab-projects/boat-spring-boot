package xyz.srclab.spring.boot.bean

import javax.annotation.PostConstruct

open class CoreBean {

    @PostConstruct
    private fun init() {
        println(">>>>>>>>>>> Core Bean!")
    }
}