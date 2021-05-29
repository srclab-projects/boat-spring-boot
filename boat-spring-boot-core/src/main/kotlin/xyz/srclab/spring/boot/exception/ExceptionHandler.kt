package xyz.srclab.spring.boot.exception

/**
 * Handler to convert throwable object [E] to state object [T].
 *
 * @see EnableExceptionHandlingService
 */
interface ExceptionHandler<E : Throwable, T : Any> {

    fun handle(e: E): T
}