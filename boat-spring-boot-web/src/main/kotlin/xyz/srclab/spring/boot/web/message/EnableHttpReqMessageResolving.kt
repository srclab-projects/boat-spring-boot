package xyz.srclab.spring.boot.web.message

import org.springframework.context.annotation.Import
import org.springframework.stereotype.Controller
import javax.servlet.http.HttpServletRequest

/**
 * Enable resolve [HttpReqMessage] by [HttpReqMessageResolver].
 *
 * [HttpReqMessageResolver] get attribute of [HttpServletRequest] with name specified by
 * [MessageProperties.httpReqMessageAttributeName], that means, you should set this attribute before current
 * [Controller].
 *
 * @see HttpReqMessage
 * @see HttpReqMessageResolver
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(HttpReqMessageResolverConfigurer::class)
annotation class EnableHttpReqMessageResolving