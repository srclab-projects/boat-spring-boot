package xyz.srclab.spring.boot.bean.test.configure

import javax.annotation.Resource

open class DynaService1 {

    @Resource
    private lateinit var dynaService2: DynaService2

    fun printInject() {
        println("DynaService1.printInject: $dynaService2")
    }
}