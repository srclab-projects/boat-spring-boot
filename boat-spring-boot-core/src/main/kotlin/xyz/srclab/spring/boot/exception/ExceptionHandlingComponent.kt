package xyz.srclab.spring.boot.exception

import org.springframework.stereotype.Component

/**
 * Annotates current class is an exception handling component.
 * This component has methods which annotated by [ExceptionHandlingMethod], and [ExceptionHandlingService] will call
 * these methods to handle matched exceptions.
 *
 * @see ExceptionHandlingMethod
 * @see EnableExceptionHandlingService
 */
@Component
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ExceptionHandlingComponent

/**
 * Annotates a method is an exception handling method to handle given exception.
 *
 * Note the method must have only one parameter, and type of that parameter must be [Throwable] or its sub-type.
 *
 * @see ExceptionHandlingComponent
 * @see EnableExceptionHandlingService
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class ExceptionHandlingMethod