package xyz.srclab.spring.boot.web.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.annotation.Resource

@ControllerAdvice
open class WebExceptionAdvice {

    @Resource
    private lateinit var webExceptionService: WebExceptionService

    @ExceptionHandler
    @ResponseBody
    open fun doException(e: Throwable): Any {
        return webExceptionService.toResponseEntity(e)
    }
}