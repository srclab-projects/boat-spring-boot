package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import xyz.srclab.spring.boot.exception.EnableExceptionStatusService
import xyz.srclab.spring.boot.exception.ExceptionStatusHandler
import xyz.srclab.spring.boot.exception.ExceptionStatusService

/**
 * Enable exception state handler function for web.
 *
 * This function can be used in global exception processing for web, it will import [EnableExceptionStatusService] and
 * use bean [ExceptionStatusService].
 *
 * @author sunqian
 *
 * @see EnableExceptionStatusService
 * @see ExceptionStatusHandler
 * @see ExceptionStatusService
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(WebExceptionAdvice::class)
annotation class EnableWebExceptionStatusService
