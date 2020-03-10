package xyz.srclab.spring.boot.bean.test.component

import javax.annotation.Resource

class DynaServiceByFactory1 {

    @Resource
    private var dynaServiceByFactory2: DynaServiceByFactory2? = null

    fun printReturn(): String {
        return printReturn(this, dynaServiceByFactory2)
    }
}