package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Import
import xyz.srclab.common.state.State

/**
 * Enable exception state handler function.
 *
 * This function can be used in global exception processing, it use bean of type [ExceptionStateHandler] to convert
 * exception to [State]. Autowire bean [ExceptionStateService] to use it.
 *
 * @author sunqian
 *
 * @see ExceptionStateService
 * @see ExceptionStateHandler
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExceptionStateService::class)
annotation class EnableExceptionStateService
