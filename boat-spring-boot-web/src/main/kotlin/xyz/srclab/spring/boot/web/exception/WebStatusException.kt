package xyz.srclab.spring.boot.web.exception

import org.springframework.http.HttpStatus
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.common.exception.StatusException

/**
 * @author sunqian
 */
open class WebStatusException @JvmOverloads constructor(
    exceptionStatus: ExceptionStatus,
    cause: Throwable? = null,
    @get:JvmName("httpStatus")
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : StatusException(exceptionStatus, cause) {

    @JvmOverloads
    constructor(httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) : this(
        ExceptionStatus.UNKNOWN, null, httpStatus
    )

    @JvmOverloads
    constructor(cause: Throwable?, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) : this(
        ExceptionStatus.INTERNAL,
        cause, httpStatus
    )

    @JvmOverloads
    constructor(
        message: String?,
        httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ) : this(ExceptionStatus.INTERNAL.withNewDescription(message), null, httpStatus)

    @JvmOverloads
    constructor(message: String?, cause: Throwable?, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) : this(
        ExceptionStatus.INTERNAL.withNewDescription(message),
        cause, httpStatus
    )

    @JvmOverloads
    constructor(
        code: String,
        description: String?,
        cause: Throwable?,
        httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ) : this(
        ExceptionStatus.of(code, description),
        cause, httpStatus
    )
}