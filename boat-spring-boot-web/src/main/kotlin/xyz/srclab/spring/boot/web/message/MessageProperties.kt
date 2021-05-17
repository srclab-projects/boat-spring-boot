package xyz.srclab.spring.boot.web.message

open class MessageProperties {

    /**
     * Attribute name of [HttpReqMessage].
     */
    var httpReqMessageAttributeName: String? = DEFAULT_HTTP_REQ_MESSAGE_ATTRIBUTE_NAME

    companion object {
        const val DEFAULT_HTTP_REQ_MESSAGE_ATTRIBUTE_NAME = "httpReqMessage"
    }
}