package xyz.srclab.spring.boot.web.message

import org.springframework.http.HttpHeaders
import xyz.srclab.spring.boot.message.RespMessage

interface HttpRespMessage<T> : RespMessage<HttpHeaders, T> {

    companion object {

        @JvmStatic
        fun <T> newHttpRespMessage(): HttpRespMessage<T> {
            return HttpRespMessageImpl()
        }

        private class HttpRespMessageImpl<T> : HttpRespMessage<T> {
            override var id: String? = null
            override var code: String? = null
            override var description: String? = null
            override var metadata: HttpHeaders? = null
            override var body: T? = null
        }
    }
}