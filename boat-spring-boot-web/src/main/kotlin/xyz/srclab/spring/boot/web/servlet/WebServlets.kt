@file:JvmName("WebServlets")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.web.servlet

import org.springframework.http.ResponseEntity
import xyz.srclab.common.base.toCharSet
import xyz.srclab.common.collect.map
import xyz.srclab.common.collect.toEnumeration
import xyz.srclab.common.serialize.json.toJsonStream
import java.io.BufferedReader
import java.io.InputStream
import java.util.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse
import kotlin.collections.LinkedHashMap

@JvmName("newPreparedHttpServletRequest")
@JvmOverloads
fun HttpServletRequest.toPreparedHttpServletRequest(
    parameters: Map<String, List<String>>,
    inputStream: ServletInputStream = this.inputStream,
): PreparedHttpServletRequest {
    return PreparedHttpServletRequest(this, parameters, inputStream)
}

@JvmName("newPreparedServletInputStream")
@JvmOverloads
fun ServletInputStream.toPreparedServletInputStream(
    inputStream: InputStream = this,
): PreparedServletInputStream {
    return PreparedServletInputStream(this, inputStream)
}

fun Map<String, *>.putRequest(request: HttpServletRequest) {
    for (entry in this) {
        request.setAttribute(entry.key, entry.value)
    }
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

open class PreparedHttpServletRequest(
    source: HttpServletRequest,
    parameters: Map<String, List<String>>,
    private val inputStream: ServletInputStream,
) : HttpServletRequestWrapper(source) {

    private val parameters: MutableMap<String, MutableList<String>> = LinkedHashMap(parameters.map { k, v ->
        k to LinkedList(v)
    })

    override fun getInputStream(): ServletInputStream {
        return inputStream
    }

    override fun getParameter(name: String): String? {
        return parameters[name]?.first()
    }

    override fun getParameterNames(): Enumeration<String> {
        return parameters.keys.toEnumeration()
    }

    override fun getParameterValues(name: String): Array<String>? {
        return parameters[name]?.toTypedArray()
    }

    override fun getParameterMap(): Map<String, Array<String>> {
        return parameters.map { k, v ->
            k to v.toTypedArray()
        }
    }

    override fun setAttribute(name: String, o: Any?) {
        super.setAttribute(name, o)
        parameters.getOrPut(name) { LinkedList() }.add(o.toString())
    }

    override fun getReader(): BufferedReader {
        return inputStream.bufferedReader(characterEncoding.toCharSet())
    }
}

open class PreparedServletInputStream(
    private val source: ServletInputStream,
    private val inputStream: InputStream,
) : ServletInputStream() {

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
}