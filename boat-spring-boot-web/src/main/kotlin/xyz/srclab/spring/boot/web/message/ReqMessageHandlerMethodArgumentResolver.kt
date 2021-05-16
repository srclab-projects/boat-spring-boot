package xyz.srclab.spring.boot.web.message

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor

open class ReqMessageHandlerMethodArgumentResolver : ServletModelAttributeMethodProcessor(true) {

    //private val modelAttributeMethodProcessor = ModelAttributeMethodProcessor(true)

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return HttpReqMessage::class.java.isAssignableFrom(parameter.parameter.type)
    }

    override fun createAttributeFromRequestValue(
        sourceValue: String,
        attributeName: String,
        parameter: MethodParameter,
        binderFactory: WebDataBinderFactory,
        request: NativeWebRequest
    ): Any? {
        return HttpReqMessage.newHttpReqMessage<Any?>()
    }

    override fun getRequestValueForAttribute(attributeName: String, request: NativeWebRequest): String? {
        return attributeName
    }
}