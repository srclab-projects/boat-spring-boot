package xyz.srclab.spring.boot.web.exception

import org.springframework.http.ResponseEntity
import xyz.srclab.common.lang.INAPPLICABLE_JVM_NAME

/**
 * Handler to convert throwable object [E] to [ResponseEntity].
 */
interface WebExceptionResponseHandler<E : Throwable> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedType: Class<out Throwable>
        @JvmName("supportedType") get

    fun handle(e: E): ResponseEntity<*>
}