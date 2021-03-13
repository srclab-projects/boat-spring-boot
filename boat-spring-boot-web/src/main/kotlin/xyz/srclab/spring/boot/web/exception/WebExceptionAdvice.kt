package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import xyz.srclab.spring.boot.exception.ExceptionStateService
import javax.annotation.Resource

@Import(ExceptionStateService::class)
@ControllerAdvice
@Component("xyz.srclab.spring.boot.web.exception.WebExceptionAdvice")
open class WebExceptionAdvice {

    @Resource
    private lateinit var exceptionStateService: ExceptionStateService

    @ExceptionHandler
    @ResponseBody
    open fun doException(e: Throwable): Any {
        return exceptionStateService.toState(e)
    }
}