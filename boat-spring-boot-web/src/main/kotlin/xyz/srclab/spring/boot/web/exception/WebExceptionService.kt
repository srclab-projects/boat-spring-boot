package xyz.srclab.spring.boot.web.exception

import org.springframework.http.ResponseEntity
import xyz.srclab.spring.boot.exception.ExceptionHandlingService
import javax.annotation.Resource

/**
 * Provides global web exception service.
 *
 * @see EnableWebExceptionService
 * @see WebExceptionResponseHandler
 */
open class WebExceptionService {

    @Resource
    private lateinit var exceptionHandlingService: ExceptionHandlingService

    /**
     * Convert given exception to [ResponseEntity].
     */
    open fun toResponseEntity(e: Throwable): ResponseEntity<*> {
        return exceptionHandlingService.handle(e, ResponseEntity::class.java)
    }
}
