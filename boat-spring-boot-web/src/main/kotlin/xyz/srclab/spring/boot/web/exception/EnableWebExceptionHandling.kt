package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
import xyz.srclab.spring.boot.exception.ExceptionHandlingService

/**
 * Enable web exception handling.
 *
 * It will open [EnableExceptionHandlingService] and use [ExceptionHandlingService] to handle uncaught exception:
 *
 * * If the exception is [ExceptionStatus] or [WebStatusException], return [ResponseEntity] created from it;
 * * If result of exception handling by [ExceptionHandlingService.handle] is [ExceptionStatus] or [WebStatusException],
 * return [ResponseEntity] created from it;
 * * If result of exception handling by [ExceptionHandlingService.handle] is [ResponseEntity], return it;
 * * Else return [ResponseEntity] created from [toExceptionResponseBody].
 *
 * Note struct of exception response body is [ExceptionResponseBody].
 *
 * @see WebStatusException
 * @see EnableExceptionHandlingService
 * @See ExceptionResponseBody
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(WebExceptionConfiguration::class)
annotation class EnableWebExceptionHandling