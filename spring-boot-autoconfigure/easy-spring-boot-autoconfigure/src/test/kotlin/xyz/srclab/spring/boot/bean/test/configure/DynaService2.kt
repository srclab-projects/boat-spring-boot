package xyz.srclab.spring.boot.bean.test.configure

import javax.annotation.Resource

open class DynaService2 {

    @Resource
    private lateinit var dynaService1: DynaService1

    fun printInject() {
        println("DynaService2.printInject: $dynaService1")
    }
}