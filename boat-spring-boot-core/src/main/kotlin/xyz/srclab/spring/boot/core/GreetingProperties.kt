package xyz.srclab.spring.boot.core

import xyz.srclab.common.lang.SgrChars
import xyz.srclab.spring.boot.BoatSpringBoot

open class GreetingProperties {

    /**
     * Whether greeting when started, default is true.
     */
    var enable: Boolean = true

    /**
     * Greeting message.
     */
    var message: String? = "${SgrChars.foregroundGreen("Start")} " +
        "${SgrChars.foregroundYellow(BoatSpringBoot.about.name.split(" ").joinToString("-"))} " +
        "${SgrChars.foregroundRed("v" + BoatSpringBoot.version)} " +
        "${SgrChars.foregroundMagenta("successful")}!"
}