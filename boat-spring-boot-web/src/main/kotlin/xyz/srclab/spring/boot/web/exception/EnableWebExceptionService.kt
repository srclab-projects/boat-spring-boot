package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity

/**
 * Enable web exception service, includes:
 *
 * * [WebExceptionService]: global web exception processing service, autowired bean to convert exception to
 * [ResponseEntity] by [WebExceptionResponseHandler].
 *
 * @see WebExceptionService
 * @see WebExceptionResponseHandler
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(WebExceptionConfiguration::class)
annotation class EnableWebExceptionService