@file:JvmName("TaskSchedulers")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.schedule

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.util.ErrorHandler
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory

@JvmName("newTaskScheduler")
fun ScheduledPoolProperties.toTaskScheduler(): TaskScheduler {
    return toTaskScheduler(null, null, null)
}

@JvmName("newTaskScheduler")
fun ScheduledPoolProperties.toTaskScheduler(
    rejectedExecutionHandler: RejectedExecutionHandler
): TaskScheduler {
    return toTaskScheduler(rejectedExecutionHandler, null, null)
}

@JvmName("newTaskScheduler")
fun ScheduledPoolProperties.toTaskScheduler(errorHandler: ErrorHandler): TaskScheduler {
    return toTaskScheduler(null, errorHandler, null)
}

@JvmName("newTaskScheduler")
fun ScheduledPoolProperties.toTaskScheduler(
    rejectedExecutionHandler: RejectedExecutionHandler,
    errorHandler: ErrorHandler
): TaskScheduler {
    return toTaskScheduler(rejectedExecutionHandler, errorHandler, null)
}

@JvmName("newTaskScheduler")
fun ScheduledPoolProperties.toTaskScheduler(
    rejectedExecutionHandler: RejectedExecutionHandler? = null,
    errorHandler: ErrorHandler? = null,
    threadFactory: ThreadFactory? = null,
): TaskScheduler {
    val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
    threadPoolTaskScheduler.poolSize = this.poolSize
    threadPoolTaskScheduler.isRemoveOnCancelPolicy = this.removeOnCancel
    val threadNamePrefix = this.threadNamePrefix
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