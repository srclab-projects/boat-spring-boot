package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Import

/**
 * Enable exception handling Service: [ExceptionHandlingService].
 *
 * @see ExceptionHandlingService
 * @see ExceptionHandlingComponent
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExceptionConfiguration::class)
annotation class EnableExceptionHandlingService