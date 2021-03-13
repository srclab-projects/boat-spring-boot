package xyz.srclab.spring.boot.exception

import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME
import xyz.srclab.common.state.State

/**
 * Handler to make [E] to [T].
 *
 * @see ExceptionHandler
 */
interface ExceptionStateHandler<E : Throwable, T : State<*, *, T>> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedExceptionType: Class<*>
        @JvmName("supportedExceptionType") get

    fun handle(e: E): T
}