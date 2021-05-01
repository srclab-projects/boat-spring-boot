package xyz.srclab.spring.boot.task

import java.util.concurrent.Executor

/**
 * An task delegate for execution operation.
 */
interface TaskDelegate {

    fun execute(executor: Executor, task: Runnable)
}