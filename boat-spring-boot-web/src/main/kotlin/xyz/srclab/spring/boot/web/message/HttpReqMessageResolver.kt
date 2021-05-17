package xyz.srclab.spring.boot.web.message

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * Resolve [HttpReqMessage] parameter. It get attribute of [HttpServletRequest] with name specified by
 * [MessageProperties.httpReqMessageAttributeName], that means, you should set this attribute before current
 * [Controller]. For example:
 * ```
 * @RequestMapping("internal/message")
 * public RespBody testMessage(HttpReqMessage<ReqBody> httpReqMessage) {
 *     RespBody respBody = new RespBody();
 *     respBody.setResp1(httpReqMessage.getBody().getReq1());
 *     respBody.setResp2(httpReqMessage.getBody().getReq2());
 *     return respBody;
 * }
 *
 * @RequestMapping("message")
 * public ModelAndView testMessage(ReqBody reqBody, HttpServletRequest servletRequest) {
 *     HttpReqMessage<ReqBody> httpReqMessage = HttpReqMessage.newHttpReqMessage();
 *     httpReqMessage.setBody(reqBody);
 *     servletRequest.setAttribute("httpReqMessage", httpReqMessage);
 *     return new ModelAndView("internal/message");
 * }
 * ```
 */
open class HttpReqMessageResolver(
    private val httpReqMessageName: String
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return HttpReqMessage::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory
    ): Any? {
        return webRequest.getAttribute(httpReqMessageName, 0)
    }
}

@Configuration
open class HttpReqMessageResolverConfigurer : WebMvcConfigurer {

    @Resource
    private lateinit var messageProperties: MessageProperties

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(
            HttpReqMessageResolver(
                messageProperties.httpReqMessageAttributeName
                    ?: MessageProperties.DEFAULT_HTTP_REQ_MESSAGE_ATTRIBUTE_NAME
            )
        )
    }
}