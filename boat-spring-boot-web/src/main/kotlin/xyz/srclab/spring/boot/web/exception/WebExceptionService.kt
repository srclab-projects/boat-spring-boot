package xyz.srclab.spring.boot.web.exception

import org.springframework.context.ApplicationContext
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
 * @see WebExceptionResponseHandler
 */
open class WebExceptionService {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    private lateinit var exceptionConverter: FastConverter<ResponseEntity<*>>

    @PostConstruct
    private fun init() {
        val handlers = LinkedList<WebExceptionResponseHandler<Throwable>>()
        for (entry in applicationContext.getBeansOfType(WebExceptionResponseHandler::class.java)) {
            handlers.add(entry.value.asAny())
        }
        exceptionConverter = FastConverter.newFastConverter(handlers.map {
            object : FastConvertHandler<ResponseEntity<*>> {

                override val supportedType: Class<*> = it.supportedType

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
        return exceptionConverter.convert(e)
    }
}
