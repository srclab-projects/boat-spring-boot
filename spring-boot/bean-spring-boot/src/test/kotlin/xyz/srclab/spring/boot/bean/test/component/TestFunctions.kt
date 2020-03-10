package xyz.srclab.spring.boot.bean.test.component

fun printReturn(any: Any, inject: Any?): String {
    val string = toInjectString(any, inject)
    println(string)
    return string
}

fun toInjectString(any: Any, inject: Any?): String {
    return "${any.javaClass}.printReturn; inject: ${inject?.javaClass}"
}