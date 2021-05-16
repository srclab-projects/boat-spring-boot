package xyz.srclab.spring.boot.message

interface ReqMessage<M, T> {

    var id: String?

    var token: String?

    var metadata: M?

    var body: T?
}