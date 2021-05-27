package xyz.srclab.spring.boot.core

import xyz.srclab.common.lang.SgrChars
import xyz.srclab.spring.boot.BoatSpringBoot

open class CoreProperties {

    /**
     * Started message.
     */
    var startedMessage: String? = "${SgrChars.foregroundGreen("Start")} " +
        "${SgrChars.foregroundYellow(BoatSpringBoot.about.name)} " +
        "${SgrChars.foregroundRed("v" + BoatSpringBoot.version)}..."

    /**
     * Ready message.
     */
    var readyMessage: String? = "${SgrChars.foregroundGreen("Start")} " +
        "${SgrChars.foregroundYellow(BoatSpringBoot.about.name)} " +
        "${SgrChars.foregroundRed("v" + BoatSpringBoot.version)} " +
        "${SgrChars.foregroundMagenta("successful")}!"
}