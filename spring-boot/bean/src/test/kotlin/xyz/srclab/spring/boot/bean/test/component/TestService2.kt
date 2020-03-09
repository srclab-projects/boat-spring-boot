package xyz.srclab.spring.boot.bean.test.component

import org.springframework.stereotype.Component

@TestAnnotation2
@Component
open class TestService2 {

    fun service(): String = "provide by $javaClass"
}