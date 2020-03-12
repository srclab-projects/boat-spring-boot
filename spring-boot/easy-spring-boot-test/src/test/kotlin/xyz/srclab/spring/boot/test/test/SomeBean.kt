package xyz.srclab.spring.boot.test.test

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.test.TestMarker
import javax.annotation.PostConstruct

@Component
open class SomeBean : TestMarker {

    @PostConstruct
    private fun init() {
        mark("666")
    }
}