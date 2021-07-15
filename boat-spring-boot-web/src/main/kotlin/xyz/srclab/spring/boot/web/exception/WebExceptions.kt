@file:JvmName("WebExceptions")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.web.exception

import xyz.srclab.common.exception.ExceptionStatus

/**
 * Returns an [ExceptionResponseBody] of which code is [ExceptionStatus.INTERNAL], description is message.
 */
fun Exception.toExceptionResponseBody(): ExceptionResponseBody {
    if (this is ExceptionStatus) {
        return (this as ExceptionStatus).toExceptionResponseBody()
    }
    val exceptionResponseBody = ExceptionResponseBody()
    exceptionResponseBody.code = ExceptionStatus.INTERNAL.code
    exceptionResponseBody.description = this.message
    return exceptionResponseBody
}

/**
 * Returns an [ExceptionResponseBody] of which code is [this.code], description is [this.description].
 */
fun ExceptionStatus.toExceptionResponseBody(): ExceptionResponseBody {
    val exceptionResponseBody = ExceptionResponseBody()
    exceptionResponseBody.code = this.code
    exceptionResponseBody.description = this.description
    return exceptionResponseBody
}