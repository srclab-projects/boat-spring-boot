package xyz.srclab.spring.boot.task

open class ThreadPoolProperties {
    var corePoolSize: Int = 1
    var maxPoolSize: Int = Int.MAX_VALUE
    var keepAliveSeconds: Int = 60
    var queueCapacity: Int = Int.MAX_VALUE
    var allowCoreThreadTimeOut: Boolean = false
    var threadNamePrefix: String? = null
    var runWithCurrentThreadIfReject: Boolean = false
}