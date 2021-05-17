package sample.kotlin.xyz.srclab.spring.boot.web.message

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.serialize.json.toJsonString
import xyz.srclab.spring.boot.web.message.EnableHttpReqMessageResolving
import xyz.srclab.spring.boot.web.message.HttpReqMessage
import xyz.srclab.spring.boot.web.message.HttpReqMessage.Companion.newHttpReqMessage
import xyz.srclab.spring.boot.web.servlet.toHttpHeaders
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@SpringBootTest(classes = [Starter::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableHttpReqMessageResolving
open class WebMessageSample : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private val port = 0

    @Resource
    private val restTemplate: TestRestTemplate? = null

    @Test
    fun testMessage() {
        val result = restTemplate!!.getForObject(
            "http://localhost:$port/test/message?req1=req1&req2=req2",
            String::class.java
        )
        Companion.logger.info("/test/exception?req1=req1&req2=req2: $result")
        val respBody = RespBody()
        respBody.resp1 = "req1"
        respBody.resp2 = "req2"
        Assert.assertEquals(result, respBody.toJsonString())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebMessageSample::class.java)
    }
}

@RequestMapping("test")
@RestController
open class MessageController {

    @RequestMapping("internal/message")
    fun testMessage(httpReqMessage: HttpReqMessage<ReqBody>): RespBody {
        val respBody = RespBody()
        respBody.resp1 = httpReqMessage.body!!.req1
        respBody.resp2 = httpReqMessage.body!!.req2
        return respBody
    }

    @RequestMapping("message")
    fun testMessage(reqBody: ReqBody?, servletRequest: HttpServletRequest): ModelAndView {
        val httpReqMessage = newHttpReqMessage<ReqBody>()
        httpReqMessage.metadata = servletRequest.toHttpHeaders()
        httpReqMessage.body = reqBody
        servletRequest.setAttribute("httpReqMessage", httpReqMessage)
        return ModelAndView("internal/message")
    }
}

open class ReqBody {
    var req1: String? = null
    var req2: String? = null
}

open class RespBody {
    var resp1: String? = null
    var resp2: String? = null
}

@SpringBootApplication
open class Starter