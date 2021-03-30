package xyz.srclab.spring.boot.exception

import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME
import xyz.srclab.common.state.State

/**
 * Handler to convert throwable object [E] to state object [T].
 *
 * @see EnableExceptionService
 */
interface ExceptionStatusHandler<E : Throwable, T : State<*, *, T>> {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val supportedExceptionType: Class<out Throwable>
        @JvmName("supportedExceptionType") get

    fun handle(e: E): T
}