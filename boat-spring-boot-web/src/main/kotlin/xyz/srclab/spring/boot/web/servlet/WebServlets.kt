@file:JvmName("WebServlets")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.web.servlet

import org.springframework.http.ResponseEntity
import xyz.srclab.common.base.toCharSet
import xyz.srclab.common.collect.map
import xyz.srclab.common.collect.toEnumeration
import xyz.srclab.common.serialize.json.toJsonStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

@JvmName("newCachedHttpServletRequest")
fun HttpServletRequest.toCachedHttpServletRequest(
    parameterCache: Map<String, List<String>>
): CachedHttpServletRequest {
    return CachedHttpServletRequest(this, parameterCache)
}

@JvmName("newCachedServletInputStream")
@JvmOverloads
fun ServletInputStream.toCachedServletInputStream(body: ByteArray? = null): CachedServletInputStream {
    return CachedServletInputStream(this, body)
}

@JvmOverloads
fun ResponseEntity<*>.putResponse(
    response: HttpServletResponse,
    bodyTransform: (Any?, HttpServletResponse) -> InputStream? = { obj, _ ->
        if (obj === null) null else obj.toJsonStream()
    }
) {
    response.status = this.statusCodeValue
    for (header in this.headers) {
        for (value in header.value) {
            response.setHeader(header.key, value)
        }
    }
    val input = bodyTransform(this.body, response)
    if (input !== null) {
        input.copyTo(response.outputStream)
    }
}

open class CachedHttpServletRequest(
    servlet: HttpServletRequest,
    private val parameterCache: Map<String, List<String>>,
) : HttpServletRequestWrapper(servlet) {

    private val inputStreamCache: CachedServletInputStream = CachedServletInputStream(servlet.inputStream)

    override fun getInputStream(): ServletInputStream {
        return inputStreamCache.copy()
    }

    override fun getParameter(name: String): String? {
        return parameterCache[name]?.first()
    }

    override fun getParameterNames(): Enumeration<String> {
        return parameterCache.keys.toEnumeration()
    }

    override fun getParameterValues(name: String): Array<String>? {
        return parameterCache[name]?.toTypedArray()
    }

    override fun getParameterMap(): Map<String, Array<String>> {
        return parameterCache.map { k, v ->
            k to v.toTypedArray()
        }
    }

    override fun getReader(): BufferedReader {
        return inputStream.bufferedReader(characterEncoding.toCharSet())
    }
}

open class CachedServletInputStream(
    private val source: ServletInputStream,
    private var body: ByteArray? = null,
) : ServletInputStream() {

    private val inputStream: ByteArrayInputStream by lazy {
        val bytes = body
        if (bytes === null) {
            body = source.readBytes()
            ByteArrayInputStream(body)
        } else {
            ByteArrayInputStream(bytes)
        }
    }

    override fun read(): Int {
        return inputStream.read()
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(listener: ReadListener) {
        source.setReadListener(listener)
    }

    override fun read(b: ByteArray): Int {
        return inputStream.read(b)
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return inputStream.read(b, off, len)
    }

    override fun close() {
        inputStream.close()
    }

    override fun skip(n: Long): Long {
        return inputStream.skip(n)
    }

    override fun available(): Int {
        return inputStream.available()
    }

    override fun mark(readlimit: Int) {
        inputStream.mark(readlimit)
    }

    override fun reset() {
        inputStream.reset()
    }

    override fun markSupported(): Boolean {
        return inputStream.markSupported()
    }

    fun copy(): CachedServletInputStream {
        return CachedServletInputStream(source, body)
    }
}