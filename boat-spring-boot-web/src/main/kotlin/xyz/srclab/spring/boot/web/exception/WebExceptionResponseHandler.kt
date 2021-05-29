package xyz.srclab.spring.boot.web.exception

import org.springframework.http.ResponseEntity
import xyz.srclab.spring.boot.exception.ExceptionHandler

/**
 * Handler to convert throwable object [E] to [ResponseEntity].
 */
interface WebExceptionResponseHandler<E : Throwable> : ExceptionHandler<E, ResponseEntity<*>> {
}