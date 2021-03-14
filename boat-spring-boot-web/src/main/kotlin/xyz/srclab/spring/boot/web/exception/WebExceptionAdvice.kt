package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import xyz.srclab.spring.boot.exception.ExceptionStatusService
import javax.annotation.Resource

@Import(ExceptionStatusService::class)
@ControllerAdvice
@Component("xyz.srclab.spring.boot.web.exception.WebExceptionAdvice")
open class WebExceptionAdvice {

    @Resource
    private lateinit var exceptionStatusService: ExceptionStatusService

    @ExceptionHandler
    @ResponseBody
    open fun doException(e: Throwable): Any {
        return exceptionStatusService.toState(e)
    }
}