package xyz.srclab.spring.boot.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import javax.annotation.Resource

/**
 * @author sunqian
 */
open class StartedBoatSpringBootBean : ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private lateinit var coreProperties: CoreProperties

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val startedMessage = coreProperties.startedMessage
        if (startedMessage !== null) {
            logger.info(startedMessage)
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(StartedBoatSpringBootBean::class.java)
    }
}

open class ReadyBoatSpringBootBean : ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private lateinit var coreProperties: CoreProperties

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val readyMessage = coreProperties.readyMessage
        if (readyMessage !== null) {
            logger.info(readyMessage)
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(ReadyBoatSpringBootBean::class.java)
    }
}