package xyz.srclab.spring.boot.exception

import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME
import xyz.srclab.common.state.State

/**
 * Handler to do with [E].
 *
 * @see ExceptionProcessor
 */
interface ExceptionHandler<E : Throwable, C, D, T : State<C, D, T>> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedExceptionType: Class<*>
        @JvmName("supportedExceptionType") get

    fun handle(e: E): T
}