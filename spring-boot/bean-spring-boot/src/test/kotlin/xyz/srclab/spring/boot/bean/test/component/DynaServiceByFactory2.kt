package xyz.srclab.spring.boot.bean.test.component

import javax.annotation.Resource

class DynaServiceByFactory2 {

    @Resource
    private var dynaServiceByFactory1: DynaServiceByFactory1? = null

    fun printReturn(): String {
        return printReturn(this, dynaServiceByFactory1)
    }
}