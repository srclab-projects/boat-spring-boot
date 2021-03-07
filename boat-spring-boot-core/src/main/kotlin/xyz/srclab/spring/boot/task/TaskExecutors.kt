@file:JvmName("TaskExecutors")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.task

import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory

fun newTaskExecutor(
    poolProperties: ThreadPoolProperties,
): TaskExecutor {
    return newTaskExecutor(poolProperties, null, null)
}

fun newTaskExecutor(
    poolProperties: ThreadPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler,
): TaskExecutor {
    return newTaskExecutor(poolProperties, rejectedExecutionHandler, null)
}

fun newTaskExecutor(
    poolProperties: ThreadPoolProperties,
    taskDecorator: TaskDecorator,
): TaskExecutor {
    return newTaskExecutor(poolProperties, null, taskDecorator)
}

fun newTaskExecutor(
    poolProperties: ThreadPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler,
    taskDecorator: TaskDecorator
): TaskExecutor {
    return newTaskExecutor(poolProperties, rejectedExecutionHandler, taskDecorator)
}

fun newTaskExecutor(
    poolProperties: ThreadPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler? = null,
    taskDecorator: TaskDecorator? = null,
    threadFactory: ThreadFactory? = null,
): TaskExecutor {
    val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
    threadPoolTaskExecutor.corePoolSize = poolProperties.corePoolSize
    threadPoolTaskExecutor.maxPoolSize = poolProperties.maxPoolSize
    threadPoolTaskExecutor.keepAliveSeconds = poolProperties.keepAliveSeconds
    threadPoolTaskExecutor.setQueueCapacity(poolProperties.queueCapacity)
    threadPoolTaskExecutor.setAllowCoreThreadTimeOut(poolProperties.allowCoreThreadTimeOut)
    val threadNamePrefix = poolProperties.threadNamePrefix
    if (threadNamePrefix !== null) {
        threadPoolTaskExecutor.threadNamePrefix = threadNamePrefix
    }
    if (poolProperties.runWithCurrentThreadIfReject) {
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
    return threadPoolTaskExecutor
}