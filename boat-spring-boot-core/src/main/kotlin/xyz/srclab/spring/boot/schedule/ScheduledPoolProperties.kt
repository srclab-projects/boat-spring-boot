package xyz.srclab.spring.boot.schedule

open class ScheduledPoolProperties {
    var poolSize: Int = 1
    var threadNamePrefix: String? = null
    var removeOnCancel: Boolean = false
}