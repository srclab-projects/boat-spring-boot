package xyz.srclab.spring.boot.exception

import org.springframework.context.ApplicationContext
import xyz.srclab.common.base.asAny
import xyz.srclab.common.convert.FastConvertHandler
import xyz.srclab.common.convert.FastConverter
import xyz.srclab.common.state.State
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Provides global exception service.
 *
 * @see EnableExceptionService
 * @see ExceptionStatusHandler
 */
open class ExceptionStatusService {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    private lateinit var exceptionStateConverter: FastConverter<State<*, *, *>>

    @PostConstruct
    private fun init() {
        val handlers = LinkedList<ExceptionStatusHandler<Throwable, *>>()
        for (entry in applicationContext.getBeansOfType(ExceptionStatusHandler::class.java)) {
            handlers.add(entry.value.asAny())
        }
        exceptionStateConverter = FastConverter.newFastConverter(handlers.map {
            object : FastConvertHandler<State<*, *, *>> {

                override val supportedType: Class<*> = it.supportedExceptionType

                override fun convert(from: Any): State<*, *, *> {
                    if (from !is Throwable) {
                        throw IllegalArgumentException("Not a Throwable object: ${from.javaClass}")
                    }
                    return it.handle(from)
                }
            }
        })
    }

    /**
     * Convert given exception to a State object.
     */
    open fun <T : State<*, *, *>> toState(e: Throwable): T {
        return exceptionStateConverter.convert(e).asAny()
    }
}
