package sample.kotlin.xyz.srclab.spring.boot.web.exception

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.common.serialize.json.toJsonString
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService
import xyz.srclab.spring.boot.web.exception.WebExceptionHandler
import javax.annotation.Resource

@SpringBootTest(classes = [Starter::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebExceptionService
class WebExceptionSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private val port = 0

    @Resource
    private val restTemplate: TestRestTemplate? = null

    @Test
    fun testException() {
        var result = restTemplate!!.getForObject(
            "http://localhost:$port/test/exception?body=testException",
            String::class.java
        )
        Companion.logger.info("/test/exception?body=testException: $result")
        Assert.assertEquals(result, "testException")
        result = restTemplate.getForObject(
            "http://localhost:$port/test/exception?body=testException0",
            String::class.java
        )
        Companion.logger.info("/test/exception?body=testException: $result")
        Assert.assertEquals(result, ExceptionStatus.of("102").toJsonString())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebExceptionSample::class.java)
    }
}

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

@Component
open class RuntimeExceptionStatusHandler :
    WebExceptionHandler<RuntimeException> {
    override val supportedExceptionType: Class<RuntimeException> = RuntimeException::class.java
    override fun handle(e: RuntimeException): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("102"), HttpStatus.OK)
    }
}

@Component
open class ThrowableStatusHandler : WebExceptionHandler<Throwable> {
    override val supportedExceptionType: Class<Throwable> = Throwable::class.java
    override fun handle(e: Throwable): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("101"), HttpStatus.OK)
    }
}

@SpringBootApplication
open class Starter