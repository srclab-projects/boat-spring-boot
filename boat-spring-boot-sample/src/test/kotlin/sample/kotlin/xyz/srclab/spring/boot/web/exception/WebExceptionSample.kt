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
import xyz.srclab.common.serialize.json.toJsonString
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler
import xyz.srclab.spring.boot.web.exception.WebStatusException
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class,
        RuntimeExceptionHandler::class,
        ThrowableHandler::class,
        WebStatusExceptionHandler::class,
        TestController::class
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableWebExceptionService
@EnableAutoConfiguration
open class WebExceptionSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private var port = 0

    @Resource
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testException() {
        var result = restTemplate.getForObject(
            "http://localhost:$port/test/exception?body=testException",
            String::class.java
        )
        log.info("/test/exception?body=testException: {}", result)
        Assert.assertEquals(result, TestController.ResponseMessage().toJsonString())
        result = restTemplate.getForObject(
            "http://localhost:$port/test/exception?body=testException0",
            String::class.java
        )
        log.info("/test/exception?body=testException0: {}", result)
        Assert.assertEquals(result, ExceptionStatus.of("102").toJsonString())
        val entity = restTemplate.getForEntity(
            "http://localhost:$port/test/webException?body=testWebException0",
            String::class.java
        )
        log.info("/test/webException?body=testWebException0: {}", entity)
        Assert.assertEquals(entity.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
        Assert.assertEquals(entity.body, ExceptionStatus.of("103").toJsonString())
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebExceptionSample::class.java)
    }
}

@RequestMapping("test")
@RestController
open class TestController {

    @RequestMapping("exception")
    open fun testException(body: String): ResponseMessage {
        if ("testException" == body) {
            return ResponseMessage()
        }
        throw IllegalArgumentException("Must be testException!")
    }

    @RequestMapping("webException")
    open fun testWebException(body: String): ResponseMessage {
        if ("testWebException" == body) {
            return ResponseMessage()
        }
        throw WebStatusException("Must be testWebException!")
    }

    class ResponseMessage {
        var subscription = "subscription"
        var description = "description"
    }
}

open class RuntimeExceptionHandler : WebExceptionResponseHandler<RuntimeException> {
    override fun handle(e: RuntimeException): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("102"), HttpStatus.OK)
    }
}

open class ThrowableHandler : WebExceptionResponseHandler<Throwable> {
    override fun handle(e: Throwable): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("101"), HttpStatus.OK)
    }
}

open class WebStatusExceptionHandler : WebExceptionResponseHandler<WebStatusException> {
    override fun handle(e: WebStatusException): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("103"), e.httpStatus)
    }
}