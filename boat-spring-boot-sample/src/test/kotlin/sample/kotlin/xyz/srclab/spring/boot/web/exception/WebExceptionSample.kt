package sample.kotlin.xyz.srclab.spring.boot.web.exception

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent
import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionHandling
import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody
import xyz.srclab.spring.boot.web.exception.WebStatusException
import java.util.*
import javax.annotation.Resource

@SpringBootTest(
    classes = [BoatAutoConfiguration::class, ExceptionHandler::class, TestController::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableWebExceptionHandling
@EnableAutoConfiguration
class WebExceptionSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private var port = 0

    @Resource
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testIllegalException() {
        val result = restTemplate.getForEntity(
            "http://localhost:$port/test/illegalState",
            ExceptionResponseBody::class.java
        )
        log.info("/test/illegalState: {}", result)
        Assert.assertEquals(result.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
        Assert.assertEquals(Objects.requireNonNull(result.body).code, "101")
    }

    @Test
    fun testRuntimeException() {
        val result = restTemplate.getForEntity(
            "http://localhost:$port/test/runtimeException",
            ExceptionResponseBody::class.java
        )
        log.info("/test/runtimeException: {}", result)
        Assert.assertEquals(result.statusCode, HttpStatus.BAD_REQUEST)
        Assert.assertEquals(Objects.requireNonNull(result.body).code, "102")
    }

    @Test
    fun testException() {
        val result3 = restTemplate.getForEntity(
            "http://localhost:$port/test/exception",
            String::class.java
        )
        log.info("/test/exception: {}", result3)
        Assert.assertEquals(result3.statusCode, HttpStatus.OK)
        Assert.assertEquals(result3.body, "103")
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebExceptionSample::class.java)
    }
}

@RequestMapping("test")
@RestController
class TestController {

    @RequestMapping("illegalState")
    fun testIllegalState(): Map<Any, Any> {
        throw IllegalStateException()
    }

    @RequestMapping("runtimeException")
    fun testRuntimeException(): Map<Any, Any> {
        throw RuntimeException()
    }

    @RequestMapping("exception")
    fun testException(): Map<Any, Any> {
        throw Exception()
    }
}

@ExceptionHandlingComponent
class ExceptionHandler {

    @ExceptionHandlingMethod
    fun handle(illegalStateException: IllegalStateException): Any {
        return ExceptionStatus.of("101")
    }

    @ExceptionHandlingMethod
    fun handle(runtimeException: RuntimeException): Any {
        return WebStatusException("102", "desc", null, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandlingMethod
    fun handle(exception: Exception): Any {
        return ResponseEntity("103", HttpStatus.OK)
    }
}