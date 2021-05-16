package xyz.srclab.spring.boot.message

interface RespMessage<M, T> {

    var id: String?

    var code: String?

    var description: String?

    var metadata: M?

    var body: T?
}