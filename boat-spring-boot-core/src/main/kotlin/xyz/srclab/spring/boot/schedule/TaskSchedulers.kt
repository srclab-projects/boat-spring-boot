@file:JvmName("TaskSchedulers")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.schedule

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.util.ErrorHandler
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory

fun newTaskScheduler(poolProperties: ScheduledPoolProperties): TaskScheduler {
    return newTaskScheduler(poolProperties, null, null, null)
}

fun newTaskScheduler(
    poolProperties: ScheduledPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler
): TaskScheduler {
    return newTaskScheduler(poolProperties, rejectedExecutionHandler, null, null)
}

fun newTaskScheduler(poolProperties: ScheduledPoolProperties, errorHandler: ErrorHandler): TaskScheduler {
    return newTaskScheduler(poolProperties, null, errorHandler, null)
}

fun newTaskScheduler(
    poolProperties: ScheduledPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler,
    errorHandler: ErrorHandler
): TaskScheduler {
    return newTaskScheduler(poolProperties, rejectedExecutionHandler, errorHandler, null)
}

fun newTaskScheduler(
    poolProperties: ScheduledPoolProperties,
    rejectedExecutionHandler: RejectedExecutionHandler? = null,
    errorHandler: ErrorHandler? = null,
    threadFactory: ThreadFactory? = null,
): TaskScheduler {
    val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
    threadPoolTaskScheduler.poolSize = poolProperties.poolSize
    threadPoolTaskScheduler.isRemoveOnCancelPolicy = poolProperties.removeOnCancel
    val threadNamePrefix = poolProperties.threadNamePrefix
    if (threadNamePrefix !== null) {
        threadPoolTaskScheduler.threadNamePrefix = threadNamePrefix
    }
    if (rejectedExecutionHandler !== null) {
        threadPoolTaskScheduler.setRejectedExecutionHandler(rejectedExecutionHandler)
    }
    if (errorHandler !== null) {
        threadPoolTaskScheduler.setErrorHandler(errorHandler)
    }
    if (threadFactory !== null) {
        threadPoolTaskScheduler.setThreadFactory(threadFactory)
    }
    threadPoolTaskScheduler.afterPropertiesSet()
    return threadPoolTaskScheduler
}