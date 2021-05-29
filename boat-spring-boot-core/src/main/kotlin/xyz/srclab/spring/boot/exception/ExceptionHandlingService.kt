package xyz.srclab.spring.boot.exception

import org.springframework.context.ApplicationContext
import xyz.srclab.common.convert.FastConvertHandler
import xyz.srclab.common.convert.FastConverter
import xyz.srclab.common.lang.asAny
import xyz.srclab.common.reflect.TypeRef
import xyz.srclab.common.reflect.rawClass
import xyz.srclab.common.reflect.toTypeSignature
import java.lang.reflect.Method
import java.lang.reflect.Type
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

    private lateinit var exceptionConverter: FastConverter

    @PostConstruct
    private fun init() {
        val handlers = LinkedList<ExceptionHandler<Throwable, *>>()
        for (entry in applicationContext.getBeansOfType(ExceptionHandler::class.java)) {
            handlers.add(entry.value.asAny())
        }

        fun Class<*>.getHandleMethod(): Method {
            val type = this.toTypeSignature(ExceptionHandler::class.java)
            val parameterType = type.actualTypeArguments[0].rawClass
            return this.getMethod("handle", parameterType)
        }

        exceptionConverter = FastConverter.newFastConverter(handlers.map {
            object : FastConvertHandler<Throwable, Any> {
                private val handleMethod: Method = it.javaClass.getHandleMethod()
                override val fromType: Class<*> = handleMethod.parameterTypes[0]
                override val toType: Class<*> = handleMethod.returnType
                override fun convert(from: Throwable): Any {
                    return it.handle(from)
                }
            }
        },
            object : FastConvertHandler<Throwable, Nothing> {
                override val fromType: Class<*> = Throwable::class.java
                override val toType: Class<*> = Nothing::class.java
                override fun convert(from: Throwable): Nothing {
                    throw IllegalArgumentException("Cannot handle exception $from")
                }
            })
    }

    open fun <T : Any> handle(e: Throwable, type: Class<T>): T {
        return exceptionConverter.convert(e, type)
    }

    open fun <T : Any> handle(e: Throwable, type: Type): T {
        return exceptionConverter.convert(e, type)
    }

    open fun <T : Any> handle(e: Throwable, type: TypeRef<T>): T {
        return exceptionConverter.convert(e, type)
    }
}
