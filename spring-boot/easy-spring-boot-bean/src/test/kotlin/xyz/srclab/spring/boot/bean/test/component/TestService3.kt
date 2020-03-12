package xyz.srclab.spring.boot.bean.test.component

import org.springframework.stereotype.Component

@TestAnnotation1
@TestAnnotation2
@Component
open class TestService3 {

    fun service(): String = "provide by $javaClass"
}