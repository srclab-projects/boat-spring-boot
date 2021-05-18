package xyz.srclab.spring.boot.exception

import xyz.srclab.common.lang.INAPPLICABLE_JVM_NAME

/**
 * Handler to convert throwable object [E] to state object [T].
 *
 * @see EnableExceptionHandlingService
 */
interface ExceptionHandler<E : Throwable, T> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedType: Class<out Throwable>
        @JvmName("supportedType") get

    fun handle(e: E): T
}