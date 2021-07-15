package xyz.srclab.spring.boot.exception

import org.springframework.context.ApplicationContext
import xyz.srclab.common.invoke.Invoker
import xyz.srclab.common.lang.INHERITANCE_COMPARATOR
import java.lang.reflect.Method
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Global exception handling service.
 *
 * It gets all beans with annotation [ExceptionHandlingComponent] and resolve their [ExceptionHandlingMethod] method to
 * handle exceptions.
 *
 * @see EnableExceptionHandlingService
 * @see ExceptionHandlingComponent
 */
open class ExceptionHandlingService {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    private val handlers: TreeMap<Class<*>, HandlingMethod> = TreeMap(COMPARATOR)

    @PostConstruct
    private fun init() {

        fun resolveExceptionHandler(handler: Any) {
            val methods = handler.javaClass.methods
            for (method in methods) {
                val exceptionHandlerMethod = method.getAnnotation(ExceptionHandlingMethod::class.java)
                if (exceptionHandlerMethod === null || method.isBridge) {
                    continue
                }
                if (method.parameterCount != 1) {
                    throw IllegalArgumentException("Exception handler method must have only one parameter.")
                }
                val exceptionClass = method.parameterTypes[0]
                if (!Throwable::class.java.isAssignableFrom(exceptionClass)) {
                    throw IllegalArgumentException("Exception handler method's'parameter must be type of Throwable.")
                }
                val existsMethod = handlers[exceptionClass]
                if (existsMethod !== null) {
                    throw IllegalArgumentException(
                        "Each type of exception must have one handling method but now there are two: " +
                            "${existsMethod.method}, $method"
                    )
                }
                handlers[exceptionClass] = HandlingMethod(handler, method, Invoker.forMethod(method))
            }
        }

        for (entry in applicationContext.getBeansWithAnnotation(ExceptionHandlingComponent::class.java)) {
            resolveExceptionHandler(entry.value)
        }
    }

    open fun <T> handle(e: Throwable): T {
        val exceptionClass = e.javaClass
        val handlingMethod = handlers[exceptionClass]
        if (handlingMethod !== null) {
            return handlingMethod.invoker.invoke(handlingMethod.component, e)
        }
        val handlingMethods = handlers
            .subMap(Throwable::class.java, true, exceptionClass, true)
            .descendingMap()
        for (handlingMethodEntry in handlingMethods) {
            val superClass = handlingMethodEntry.key
            if (superClass.isAssignableFrom(exceptionClass)) {
                val method = handlingMethodEntry.value
                return method.invoker.invoke(method.component, e)
            }
        }
        throw IllegalArgumentException("Exception handling method not found: $e")
    }

    private data class HandlingMethod(
        val component: Any,
        val method: Method,
        val invoker: Invoker
    )

    companion object {

        private val COMPARATOR: Comparator<Class<*>> = Comparator { c1, c2 ->
            -INHERITANCE_COMPARATOR.compare(c1, c2)
        }
    }
}
