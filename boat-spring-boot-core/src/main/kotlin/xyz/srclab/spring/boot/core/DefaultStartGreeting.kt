package xyz.srclab.spring.boot.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.annotation.Resource

/**
 * Default Greeting action of [StartGreeting].
 *
 * @author sunqian
 */
open class DefaultStartGreeting : StartGreeting {

    @Resource
    private lateinit var greetingProperties: GreetingProperties

    override fun doGreeting() {
        if (!greetingProperties.enable) {
            return
        }
        val greetingMessage = greetingProperties.message
        if (greetingMessage !== null) {
            logger.info(greetingMessage)
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(DefaultStartGreeting::class.java)
    }
}