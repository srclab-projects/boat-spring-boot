package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import xyz.srclab.spring.boot.exception.EnableExceptionStateService
import xyz.srclab.spring.boot.exception.ExceptionStateHandler
import xyz.srclab.spring.boot.exception.ExceptionStateService

/**
 * Enable exception state handler function for web.
 *
 * This function can be used in global exception processing for web, it will import [EnableExceptionStateService] and
 * use bean [ExceptionStateService].
 *
 * @author sunqian
 *
 * @see EnableExceptionStateService
 * @see ExceptionStateHandler
 * @see ExceptionStateService
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(WebExceptionAdvice::class)
annotation class EnableWebExceptionStateService
