package xyz.srclab.spring.boot.test.test

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.test.BeanProcessMarker
import javax.annotation.PostConstruct

@Component
open class SomeBean : BeanProcessMarker {

    @PostConstruct
    private fun init() {
        mark("666")
    }
}