package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Import
import xyz.srclab.common.state.State

/**
 * Enable exception state handler function.
 *
 * This function can be used in global exception processing, it use bean of type [ExceptionStatusHandler] to convert
 * exception to [State]. Autowire bean [ExceptionStatusService] to use it.
 *
 * @author sunqian
 *
 * @see ExceptionStatusService
 * @see ExceptionStatusHandler
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExceptionStatusService::class)
annotation class EnableExceptionStatusService
