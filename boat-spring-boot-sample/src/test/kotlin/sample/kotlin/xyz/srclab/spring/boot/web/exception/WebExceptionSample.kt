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
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler
import xyz.srclab.spring.boot.web.exception.WebStatusException
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
        log.info("/test/exception?body=testException: {}", result)
        Assert.assertEquals(
            result,
            TestController.ResponseMessage().toJsonString()
        )

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
        log.info("/test/webException?body=testWebException0: {}", result)
        Assert.assertEquals(entity.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
        Assert.assertEquals(entity.body, ExceptionStatus.of("103").toJsonString())
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebExceptionSample::class.java)
    }
}

@RequestMapping("test")
@RestController
class TestController {

    @RequestMapping("exception")
    fun testException(body: String): ResponseMessage {
        if ("testException" == body) {
            return ResponseMessage()
        }
        throw IllegalArgumentException("Must be testException!")
    }

    @RequestMapping("webException")
    fun testWebException(body: String): ResponseMessage {
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

@Component
open class RuntimeExceptionStatusHandler :
    WebExceptionResponseHandler<RuntimeException> {
    override val supportedType: Class<RuntimeException> = RuntimeException::class.java
    override fun handle(e: RuntimeException): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("102"), HttpStatus.OK)
    }
}

@Component
open class ThrowableStatusHandler : WebExceptionResponseHandler<Throwable> {
    override val supportedType: Class<Throwable> = Throwable::class.java
    override fun handle(e: Throwable): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("101"), HttpStatus.OK)
    }
}

@Component
class WebStatusExceptionHandler : WebExceptionResponseHandler<WebStatusException> {
    override val supportedType: Class<WebStatusException> = WebStatusException::class.java
    override fun handle(e: WebStatusException): ResponseEntity<ExceptionStatus> {
        return ResponseEntity(ExceptionStatus.of("103"), e.httpStatus)
    }
}

@SpringBootApplication
open class Starter