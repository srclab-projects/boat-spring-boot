package sample.kotlin.xyz.srclab.spring.boot.web.servlet

import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.ModelAndView
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody
import xyz.srclab.spring.boot.web.exception.toExceptionResponseBody
import xyz.srclab.spring.boot.web.servlet.toPreparedHttpServletRequest
import xyz.srclab.spring.boot.web.servlet.writeFromResponseEntity
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.Resource
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootTest(
    classes = [TestController::class, TestFilter::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
class ServletSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private var port = 0

    @Resource
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testServlet() {
        val result = restTemplate!!.postForObject(
            "http://localhost:$port/test/servlet",
            "ppp1",
            String::class.java
        )
        log.info("/test/servlet: $result")
        Assert.assertEquals(result, "ppp1")
    }

    @Test
    fun testIndex() {
        val result = restTemplate!!.postForObject(
            "http://localhost:$port/test/index",
            "ppp2",
            String::class.java
        )
        log.info("/test/index: $result")
        Assert.assertEquals(result, "encode: ppp2")
    }

    @Test
    fun testException() {
        val result = restTemplate!!.getForEntity(
            "http://localhost:$port/test/exception",
            ExceptionResponseBody::class.java
        )
        log.info("/test/exception: $result")
        log.info(
            "/test/exception: " + restTemplate.getForObject(
                "http://localhost:$port/test/exception",
                String::class.java
            )
        )
        Assert.assertEquals(Objects.requireNonNull(result.body).code, ExceptionStatus.INTERNAL.code)
    }

    companion object {
        private val log = LoggerFactory.getLogger(ServletSample::class.java)
    }
}

@RequestMapping("test")
@RestController
class TestController {

    @RequestMapping("servlet")
    fun testServlet(p1: String): String {
        return p1
    }

    @RequestMapping("index")
    fun testIndex(p1: String?): ModelAndView {
        val model: MutableMap<String, Any?> = HashMap()
        model["pm"] = p1
        return ModelAndView("encode", model)
    }

    @RequestMapping("encode")
    fun testEncode(pm: String): String {
        return "encode: $pm"
    }

    @RequestMapping("exception")
    fun testException(): String {
        throw RuntimeException("hello")
    }
}

class TestFilter : OncePerRequestFilter() {

    @Resource
    private lateinit var mappingJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val p1 = IOUtils.toString(request.inputStream, StandardCharsets.UTF_8)
        val parameters: MutableMap<String, List<String>> = java.util.HashMap()
        parameters["p1"] = listOf(p1)
        val newRequest: HttpServletRequest = request.toPreparedHttpServletRequest(parameters)
        try {
            filterChain.doFilter(newRequest, response)
        } catch (e: Exception) {
            val header = HttpHeaders()
            header[HttpHeaders.CONTENT_TYPE] = "application/json"
            val responseEntity = ResponseEntity(
                e.toExceptionResponseBody(),
                header,
                HttpStatus.INTERNAL_SERVER_ERROR
            )
            response.writeFromResponseEntity(responseEntity) { body: Any?, out: ServletOutputStream? ->
                try {
                    mappingJackson2HttpMessageConverter.objectMapper.writeValue(out, body)
                } catch (ioException: IOException) {
                    throw IllegalStateException(ioException)
                }
            }
        }
    }
}