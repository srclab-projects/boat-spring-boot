package xyz.srclab.spring.boot.exception

import org.springframework.context.ApplicationContext
import xyz.srclab.common.convert.FastConvertHandler
import xyz.srclab.common.convert.FastConverter
import xyz.srclab.common.lang.asAny
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Provides global exception service.
 *
 * @see EnableExceptionHandlingService
 * @see ExceptionHandler
 */
open class ExceptionHandlingService {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    private lateinit var exceptionStateConverter: FastConverter<Any?>

    @PostConstruct
    private fun init() {
        val handlers = LinkedList<ExceptionHandler<Throwable, *>>()
        for (entry in applicationContext.getBeansOfType(ExceptionHandler::class.java)) {
            handlers.add(entry.value.asAny())
        }
        exceptionStateConverter = FastConverter.newFastConverter(handlers.map {
            object : FastConvertHandler<Any?> {

                override val supportedType: Class<*> = it.supportedType

                override fun convert(from: Any): Any? {
                    if (from !is Throwable) {
                        throw IllegalArgumentException("Not a Throwable object: ${from.javaClass}")
                    }
                    return it.handle(from)
                }
            }
        })
    }

    /**
     * Convert given exception to object.
     */
    open fun <T> toState(e: Throwable): T {
        return exceptionStateConverter.convert(e).asAny()
    }
}
