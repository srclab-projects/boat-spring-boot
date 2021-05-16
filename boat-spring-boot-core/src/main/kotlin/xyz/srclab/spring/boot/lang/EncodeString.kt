package xyz.srclab.spring.boot.lang

import xyz.srclab.common.codec.Codec
import xyz.srclab.common.lang.Default
import xyz.srclab.common.lang.asAny
import xyz.srclab.common.lang.toChars
import xyz.srclab.spring.boot.lang.EncodeString.Companion.parseEncodeString
import java.beans.PropertyEditorSupport
import java.nio.charset.Charset

/**
 * Represents encode string may be encoded and encrypted as configure property.
 */
open class EncodeString {

    /**
     * Encrypt algorithm.
     */
    var encrypt: String? = null

    /**
     * Encode algorithm.
     */
    var encode: String? = null

    /**
     * Configured value.
     */
    var value: String? = null

    fun decode(key: Any): ByteArray {
        val value = this.value
        if (value.isNullOrEmpty()) {
            return ByteArray(0)
        }
        val encrypt = this.encrypt
        val encode = this.encode
        val codec = Codec.forData(value)
        if (!encode.isNullOrEmpty()) {
            codec.decode(encode)
        }
        if (!encrypt.isNullOrEmpty()) {
            codec.decrypt(key, encrypt)
        }
        return codec.doFinal()
    }

    @JvmOverloads
    fun decodeString(key: Any, charset: Charset? = null): String {
        return decode(key).toChars(charset ?: Default.charset)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is EncodeString) {
            return false
        }
        return encrypt == other.encrypt
            && encode == other.encode
            && value == other.value
    }

    override fun hashCode(): Int {
        return encrypt.hashCode() + encode.hashCode() + value.hashCode()
    }

    override fun toString(): String {
        return "$encrypt,$encode,$value"
    }

    companion object {

        /**
         * Parse from:
         * ```
         * encrypt,encode:value
         * ```
         * For example:
         * ```
         * AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==
         * ```
         * will be decode value as `some password` with key `123`.
         */
        @JvmName("parse")
        @JvmStatic
        fun CharSequence.parseEncodeString(): EncodeString {
            val encodeString = EncodeString()
            val colonIndex = this.indexOf(":")
            if (colonIndex < 0) {
                return encodeString
            }
            encodeString.value = this.substring(colonIndex + 1)
            val commaSpit = this.subSequence(0, colonIndex).split(",")
            if (commaSpit.size != 2) {
                throw IllegalArgumentException(
                    "Wrong EncodeString format, right format is encrypt,encode:value but now is: $this"
                )
            }
            encodeString.encrypt = commaSpit[0].trim().ifEmpty { null }
            encodeString.encode = commaSpit[1].trim().ifEmpty { null }
            return encodeString
        }
    }
}

open class EncodeStringEditor : PropertyEditorSupport() {

    override fun getAsText(): String {
        val encodeString = value.asAny<EncodeString>()
        return encodeString.toString()
    }

    override fun setAsText(text: String?) {
        if (text.isNullOrEmpty()) {
            value = null
            return
        }
        value = text.parseEncodeString()
    }
}