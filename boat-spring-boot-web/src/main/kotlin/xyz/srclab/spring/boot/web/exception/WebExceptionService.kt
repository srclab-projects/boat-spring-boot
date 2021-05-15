package xyz.srclab.spring.boot.web.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import xyz.srclab.common.convert.FastConvertHandler
import xyz.srclab.common.convert.FastConverter
import xyz.srclab.common.lang.asAny
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Provides global web exception service.
 *
 * @see EnableWebExceptionService
 * @see WebExceptionHandler
 */
open class WebExceptionService {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    private lateinit var exceptionConverter: FastConverter<ResponseEntity<*>>

    @PostConstruct
    private fun init() {
        val handlers = LinkedList<WebExceptionHandler<Throwable>>()
        for (entry in applicationContext.getBeansOfType(WebExceptionHandler::class.java)) {
            handlers.add(entry.value.asAny())
        }
        exceptionConverter = FastConverter.newFastConverter(handlers.map {
            object : FastConvertHandler<ResponseEntity<*>> {

                override val supportedType: Class<*> = it.supportedExceptionType

                override fun convert(from: Any): ResponseEntity<*> {
                    if (from !is Throwable) {
                        throw IllegalArgumentException("Not a Throwable object: ${from.javaClass}")
                    }
                    return it.handle(from)
                }
            }
        })
    }

    /**
     * Convert given exception to [ResponseEntity].
     */
    open fun toResponseEntity(e: Throwable): ResponseEntity<*> {
        return try {
            exceptionConverter.convert(e)
        } catch (t: Throwable) {
            logger.error("Convert exception to ResponseEntity error: ", t)
            logger.error("Original error: ", e)
            ResponseEntity<Any?>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(WebExceptionService::class.java)
    }
}
