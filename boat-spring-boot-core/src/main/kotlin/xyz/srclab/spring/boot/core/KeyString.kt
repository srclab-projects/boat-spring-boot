package xyz.srclab.spring.boot.core

import xyz.srclab.common.codec.Codecing.Companion.startCodec
import xyz.srclab.common.lang.Defaults
import xyz.srclab.common.lang.asAny
import xyz.srclab.common.lang.toChars
import xyz.srclab.spring.boot.core.KeyString.Companion.parseKeyString
import java.beans.PropertyEditor
import java.beans.PropertyEditorSupport
import java.nio.charset.Charset

/**
 * Represents a key string which is encrypted by [encode] algorithm, then encoded by [encode] algorithm.
 */
open class KeyString {

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
        val codec = value.startCodec()
        if (!encode.isNullOrEmpty()) {
            codec.decode(encode)
        }
        if (!encrypt.isNullOrEmpty()) {
            codec.decrypt(encrypt, key)
        }
        return codec.doFinal()
    }

    @JvmOverloads
    fun decodeString(key: Any, charset: Charset = Defaults.charset): String {
        return decode(key).toChars(charset)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is KeyString) {
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
        return "$encrypt,$encode:$value"
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
        fun CharSequence.parseKeyString(): KeyString {
            val keyString = KeyString()
            val colonIndex = this.indexOf(":")
            if (colonIndex < 0) {
                return keyString
            }
            keyString.value = this.substring(colonIndex + 1)
            val commaSpit = this.subSequence(0, colonIndex).split(",")
            if (commaSpit.size != 2) {
                throw IllegalArgumentException(
                    "Wrong EncodeString format, right format is encrypt,encode:value but now is: $this"
                )
            }
            keyString.encrypt = commaSpit[0].trim().ifEmpty { null }
            keyString.encode = commaSpit[1].trim().ifEmpty { null }
            return keyString
        }
    }
}

/**
 * [PropertyEditor] for [KeyString].
 */
open class KeyStringEditor : PropertyEditorSupport() {

    override fun getAsText(): String {
        val keyString = value.asAny<KeyString>()
        return keyString.toString()
    }

    override fun setAsText(text: String?) {
        if (text.isNullOrEmpty()) {
            value = null
            return
        }
        value = text.parseKeyString()
    }
}