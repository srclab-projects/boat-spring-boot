package xyz.srclab.spring.boot.bean.test.component

import javax.annotation.Resource

open class DynaService2 {

    @Resource
    private lateinit var dynaService1: DynaService1

    fun printReturn(): String {
        return printReturn(this, dynaService1)
    }
}