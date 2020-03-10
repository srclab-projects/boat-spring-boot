package xyz.srclab.spring.boot.common.test.model

open class Bird {

    open fun fly(): String {
        println("fly of $javaClass")
        return "Bird.fly"
    }

    open fun blow(): String {
        println("blow of $javaClass")
        return "Bird.blow"
    }

    open fun call(): String {
        println("call of $javaClass")
        return "Bird.call"
    }
}