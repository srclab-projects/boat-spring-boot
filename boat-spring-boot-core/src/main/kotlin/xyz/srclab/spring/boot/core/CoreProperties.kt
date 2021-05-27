package xyz.srclab.spring.boot.core

import xyz.srclab.common.lang.SgrChars
import xyz.srclab.spring.boot.BoatSpringBoot

open class CoreProperties {

    /**
     * Whether greeting when started, default is true.
     */
    var greeting: Boolean = true

    /**
     * Greeting message.
     */
    var greetingMessage: String? = "${SgrChars.foregroundGreen("Start")} " +
        "${SgrChars.foregroundYellow(BoatSpringBoot.about.name)} " +
        "${SgrChars.foregroundRed("v" + BoatSpringBoot.version)} " +
        "${SgrChars.foregroundMagenta("successful")}!"
}