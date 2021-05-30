package sample.kotlin.xyz.srclab.spring.boot.web.servlet

import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.ModelAndView
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService
import xyz.srclab.spring.boot.web.exception.WebExceptionService
import xyz.srclab.spring.boot.web.servlet.toPreparedHttpServletRequest
import xyz.srclab.spring.boot.web.servlet.writeResponseEntity
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.annotation.Resource
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, TestController::class, TestFilter::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
open class ServletSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private var port = 0

    @Resource
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testServlet() {
        var result = restTemplate.postForObject(
            "http://localhost:$port/test/servlet",
            "ppp1",
            String::class.java
        )
        log.info("/test/servlet: $result")
        Assert.assertEquals(result, "ppp1")
        result = restTemplate.postForObject(
            "http://localhost:$port/test/index",
            "ppp2",
            String::class.java
        )
        log.info("/test/index: $result")
        Assert.assertEquals(result, "encode: ppp2")
    }

    companion object {
        private val log = LoggerFactory.getLogger(ServletSample::class.java)
    }
}

@RequestMapping("test")
@RestController
open class TestController {

    @RequestMapping("servlet")
    open fun testServlet(p1: String): String {
        return p1
    }

    @RequestMapping("index")
    open fun testIndex(p1: String?): ModelAndView {
        val model: MutableMap<String, Any?> = HashMap()
        model["pm"] = p1
        return ModelAndView("encode", model)
    }

    @RequestMapping("encode")
    open fun testEncode(pm: String): String {
        return "encode: $pm"
    }
}

@EnableWebExceptionService
open class TestFilter : OncePerRequestFilter() {

    @Resource
    private lateinit var webExceptionService: WebExceptionService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val p1 = IOUtils.toString(request.inputStream, StandardCharsets.UTF_8)
        val parameters: MutableMap<String, List<String>> = HashMap()
        parameters["p1"] = listOf(p1)
        val newRequest: HttpServletRequest = request.toPreparedHttpServletRequest(parameters)
        try {
            filterChain.doFilter(newRequest, response)
        } catch (e: Throwable) {
            response.writeResponseEntity(webExceptionService.toResponseEntity(e))
        }
    }
}