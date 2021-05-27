package xyz.srclab.spring.boot.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import javax.annotation.Resource

/**
 * Print greeting message when application started.
 *
 * @author sunqian
 */
open class ReadyBoatSpringBootBean : ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private lateinit var coreProperties: CoreProperties

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        if (!coreProperties.greeting) {
            return
        }
        val greetingMessage = coreProperties.greetingMessage
        if (greetingMessage !== null) {
            logger.info(greetingMessage)
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(ReadyBoatSpringBootBean::class.java)
    }
}