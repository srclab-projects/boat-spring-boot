@file:JvmName("TaskExecutors")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.task

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.*

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(): TaskExecutor {
    return toTaskExecutor(null, null, null, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        rejectedExecutionHandler: RejectedExecutionHandler,
): TaskExecutor {
    return toTaskExecutor(rejectedExecutionHandler, null, null, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        taskDelegate: TaskDelegate,
): TaskExecutor {
    return toTaskExecutor(null, taskDelegate, null, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        taskDecorator: TaskDecorator,
): TaskExecutor {
    return toTaskExecutor(null, null, taskDecorator, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        rejectedExecutionHandler: RejectedExecutionHandler,
        taskDelegate: TaskDelegate
): TaskExecutor {
    return toTaskExecutor(rejectedExecutionHandler, taskDelegate, null, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        rejectedExecutionHandler: RejectedExecutionHandler,
        taskDecorator: TaskDecorator
): TaskExecutor {
    return toTaskExecutor(rejectedExecutionHandler, null, taskDecorator, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        rejectedExecutionHandler: RejectedExecutionHandler,
        taskDelegate: TaskDelegate,
        taskDecorator: TaskDecorator
): TaskExecutor {
    return toTaskExecutor(rejectedExecutionHandler, taskDelegate, taskDecorator, null)
}

@JvmName("newTaskExecutor")
fun TaskPoolProperties.toTaskExecutor(
        rejectedExecutionHandler: RejectedExecutionHandler? = null,
        taskDelegate: TaskDelegate? = null,
        taskDecorator: TaskDecorator? = null,
        threadFactory: ThreadFactory? = null,
): TaskExecutor {
    val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
    threadPoolTaskExecutor.corePoolSize = this.corePoolSize
    threadPoolTaskExecutor.maxPoolSize = this.maxPoolSize
    threadPoolTaskExecutor.keepAliveSeconds = this.keepAliveSeconds
    threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity)
    threadPoolTaskExecutor.setAllowCoreThreadTimeOut(this.allowCoreThreadTimeOut)
    val threadNamePrefix = this.threadNamePrefix
    if (threadNamePrefix !== null) {
        threadPoolTaskExecutor.threadNamePrefix = threadNamePrefix
    }
    if (this.runWithCurrentThreadIfReject) {
        threadPoolTaskExecutor.setRejectedExecutionHandler { r, _ -> r.run() }
    } else if (rejectedExecutionHandler !== null) {
        threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler)
    }
    if (taskDecorator !== null) {
        threadPoolTaskExecutor.setTaskDecorator(taskDecorator)
    }
    if (threadFactory !== null) {
        threadPoolTaskExecutor.setThreadFactory(threadFactory)
    }
    threadPoolTaskExecutor.afterPropertiesSet()
    return if (taskDelegate === null)
        threadPoolTaskExecutor
    else DelegatedTaskExecutor(
            threadPoolTaskExecutor,
            taskDelegate
    )
}

@JvmOverloads
fun executeWithMdc(
        executor: Executor,
        task: Runnable,
        recoverAction: (
                currentContext: Map<String, String>,
                executorContext: Map<String, String>
        ) -> Unit = { _, executorContext ->
            MDC.clear()
            MDC.setContextMap(executorContext)
        }
) {
    val currentContext = MDC.getCopyOfContextMap() ?: emptyMap()
    executor.execute {
        val executorContext = MDC.getCopyOfContextMap() ?: emptyMap()
        MDC.setContextMap(currentContext)
        task.run()
        recoverAction(currentContext, executorContext)
    }
}

@JvmOverloads
fun <T> executeWithMdc(
        executor: ExecutorService,
        task: Callable<T>,
        recoverAction: (
                currentContext: Map<String, String>,
                executorContext: Map<String, String>
        ) -> Unit = { _, executorContext ->
            MDC.clear()
            MDC.setContextMap(executorContext)
        }
): Future<T> {
    val currentContext = MDC.getCopyOfContextMap() ?: emptyMap()
    val delegateTask: Callable<T> = Callable {
        val executorContext = MDC.getCopyOfContextMap() ?: emptyMap()
        MDC.setContextMap(currentContext)
        val result = task.call()
        recoverAction(currentContext, executorContext)
        result
    }
    return executor.submit(delegateTask)
}

private class DelegatedTaskExecutor(
        private val executor: Executor,
        private val taskDelegate: TaskDelegate,
) : TaskExecutor {

    override fun execute(task: Runnable) {
        taskDelegate.execute(executor, task)
    }
}