package sample.kotlin.xyz.srclab.spring.boot.bean

import javax.annotation.Resource

open class MyBean {

    @Resource
    private lateinit var bean1: String

    @Resource
    private lateinit var bean2: String

    val beanString: String
        get() = bean1 + bean2
}