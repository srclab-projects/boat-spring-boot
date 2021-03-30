package xyz.srclab.spring.boot.web.exception

import org.springframework.http.ResponseEntity
import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME

/**
 * Handler to convert throwable object [E] to [ResponseEntity].
 */
interface WebExceptionHandler<E : Throwable> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedExceptionType: Class<out Throwable>
        @JvmName("supportedExceptionType") get

    fun handle(e: E): ResponseEntity<*>
}