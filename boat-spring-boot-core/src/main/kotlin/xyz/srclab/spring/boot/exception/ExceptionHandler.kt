package xyz.srclab.spring.boot.exception

import xyz.srclab.common.collect.sorted
import xyz.srclab.common.state.State

/**
 * Processor used to do with [Throwable] by a group of [ExceptionStateHandler]s.
 *
 * @see ExceptionStateHandler
 */
open class ExceptionHandler<T : State<*, *, T>>() {
}