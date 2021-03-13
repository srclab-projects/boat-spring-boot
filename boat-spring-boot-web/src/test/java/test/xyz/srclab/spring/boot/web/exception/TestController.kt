package test.xyz.srclab.spring.boot.web.exception

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("test")
@RestController
open class TestController {

    @RequestMapping("exception")
    open fun testException(body: String): String {
        if (body == "testException") {
            return body
        }
        throw IllegalArgumentException("Must be testException!")
    }
}