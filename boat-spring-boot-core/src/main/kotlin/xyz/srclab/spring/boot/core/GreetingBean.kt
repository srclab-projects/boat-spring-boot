package xyz.srclab.spring.boot.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.SmartLifecycle
import javax.annotation.Resource

/**
 * @author sunqian
 */
open class GreetingBean : SmartLifecycle {

    @Resource
    private lateinit var coreProperties: CoreProperties

    private var isRunning = false

    override fun start() {
        val greetingMessage = coreProperties.greetingMessage
        if (greetingMessage !== null) {
            logger.info(greetingMessage)
        }
        isRunning = true
    }

    override fun stop() {
    }

    override fun isRunning(): Boolean {
        return isRunning
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(GreetingBean::class.java)
    }
}