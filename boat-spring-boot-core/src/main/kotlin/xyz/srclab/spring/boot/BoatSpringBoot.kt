package xyz.srclab.spring.boot

import xyz.srclab.common.lang.loadPropertiesResource
import xyz.srclab.common.utils.About
import xyz.srclab.common.utils.Author
import xyz.srclab.common.utils.SemVer
import xyz.srclab.common.utils.SemVer.Companion.parseSemVer

object BoatSpringBoot {

    private val buildInfos: Map<String, String> = "META-INF/build.properties".loadPropertiesResource()

    private val sunqian = Author.of(
        "Sun Qian",
        "fredsuvn@163.com",
        "https://github.com/fredsuvn"
    )

    private val srclab = Author.of(
        "SrcLab",
        "srclab@163.com",
        "https://github.com/srclab-projects"
    )

    @get:JvmName("version")
    @JvmStatic
    val version: SemVer = buildInfos["build.version"]!!.parseSemVer()

    @get:JvmName("about")
    @JvmStatic
    val about: About = About.of(
        "Boat Spring Boot",
        version.toString(),
        listOf(sunqian, srclab),
        srclab.mail,
        "https://github.com/srclab-projects/boat-spring-boot",
        listOf("Apache 2.0 license"),
        emptyList(),
        "© 2021 SrcLab"
    )
}