package xyz.srclab.spring.boot.web.message

import xyz.srclab.spring.boot.message.ReqMessage

interface HttpReqMessage<T> : ReqMessage<String, T> {

    companion object {

        @JvmStatic
        fun <T> newHttpReqMessage(): HttpReqMessage<T> {
            return HttpReqMessageImpl()
        }

        private class HttpReqMessageImpl<T> : HttpReqMessage<T> {
            override var id: String? = null
            override var token: String? = null
            override var metadata: String? = null
            override var body: T? = null
        }
    }
}