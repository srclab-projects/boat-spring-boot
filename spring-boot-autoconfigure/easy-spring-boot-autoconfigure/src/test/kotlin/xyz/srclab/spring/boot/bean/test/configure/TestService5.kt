package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.stereotype.Component

@Component
open class TestService5 {

    fun service(): String = "provide by $javaClass"
}