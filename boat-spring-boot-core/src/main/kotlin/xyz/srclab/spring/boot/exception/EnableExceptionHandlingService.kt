package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Import

/**
 * Enable exception service, include:
 *
 * * [ExceptionHandlingService]: autowired bean to convert exception to specified object by [ExceptionHandler].
 *
 * @see ExceptionHandlingService
 * @see ExceptionHandler
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExceptionConfiguration::class)
annotation class EnableExceptionHandlingService