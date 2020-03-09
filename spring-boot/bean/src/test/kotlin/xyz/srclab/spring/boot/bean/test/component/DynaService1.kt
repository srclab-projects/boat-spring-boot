package xyz.srclab.spring.boot.bean.test.component

import javax.annotation.Resource

open class DynaService1 {

    @Resource
    private lateinit var dynaService2: DynaService2

    fun printReturn(): String {
        return printReturn(this, dynaService2)
    }
}