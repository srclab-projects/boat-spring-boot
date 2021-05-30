package xyz.srclab.spring.boot.core

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import javax.annotation.Resource

/**
 * Do greeting when application was started.
 *
 * @author sunqian
 *
 * @see StartGreeting
 */
open class BoatSpringBootStartedBean : ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private lateinit var applicationContext: ApplicationContext

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val startGreeting = applicationContext.getBean(StartGreeting::class.java)
        startGreeting.doGreeting()
    }
}