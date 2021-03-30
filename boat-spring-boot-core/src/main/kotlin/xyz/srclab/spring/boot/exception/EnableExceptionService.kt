package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Import
import xyz.srclab.common.state.State

/**
 * Enable exception service, include:
 *
 * * [ExceptionStatusService]: autowired bean to convert exception to [State] by [ExceptionStatusHandler].
 *
 * @author sunqian
 *
 * @see ExceptionStatusService
 * @see ExceptionStatusHandler
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExceptionServiceConfiguration::class)
annotation class EnableExceptionService