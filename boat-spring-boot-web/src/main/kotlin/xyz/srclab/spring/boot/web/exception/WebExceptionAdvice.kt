package xyz.srclab.spring.boot.web.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.exception.ExceptionHandlingService
import javax.annotation.Resource

@ControllerAdvice
open class WebExceptionAdvice {

    @Resource
    private lateinit var exceptionHandlingService: ExceptionHandlingService

    @ExceptionHandler
    @ResponseBody
    open fun doException(e: Exception): Any {
        if (e is ExceptionStatus) {
            return statusExceptionToResponse((e))
        }
        val result = exceptionHandlingService.handle<Any?>(e)
        if (result is ExceptionStatus) {
            return statusExceptionToResponse((result))
        }
        if (result is ResponseEntity<*>) {
            return result
        }
        val exceptionResponseBody = e.toExceptionResponseBody()
        return ResponseEntity(exceptionResponseBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun statusExceptionToResponse(exceptionStatus: ExceptionStatus): ResponseEntity<Any?> {
        val exceptionResponseBody = exceptionStatus.toExceptionResponseBody()
        return if (exceptionStatus is WebStatusException) {
            ResponseEntity(exceptionResponseBody, exceptionStatus.httpStatus)
        } else {
            ResponseEntity(exceptionResponseBody, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}