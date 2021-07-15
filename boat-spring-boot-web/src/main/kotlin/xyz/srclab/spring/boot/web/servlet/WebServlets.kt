@file:JvmName("WebServlets")
@file:JvmMultifileClass

package xyz.srclab.spring.boot.web.servlet

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import xyz.srclab.common.collect.MutableListMap
import xyz.srclab.common.collect.MutableListMap.Companion.toMutableListMap
import xyz.srclab.common.collect.map
import xyz.srclab.common.collect.toEnumeration
import xyz.srclab.common.lang.toCharSet
import xyz.srclab.common.serialize.json.toJson
import java.io.BufferedReader
import java.io.InputStream
import java.util.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

fun HttpServletRequest.toHttpHeaders(): HttpHeaders {
    val headers = HttpHeaders()
    for (headerName in this.headerNames) {
        for (header in this.getHeaders(headerName)) {
            headers.add(headerName, header)
        }
    }
    return headers
}

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

fun HttpServletRequest.setAttributes(attributes: Map<String, *>) {
    for (attribute in attributes) {
        this.setAttribute(attribute.key, attribute.value)
    }
}

@JvmOverloads
fun HttpServletResponse.writeResponseEntity(
    responseEntity: ResponseEntity<*>,
    writeAction: (Any?, ServletOutputStream) -> Unit = block@{ body, out ->
        if (body === null) {
            return@block
        }
        body.toJson().writeTo(out)
    }
) {
    this.status = responseEntity.statusCodeValue
    for (header in responseEntity.headers) {
        for (value in header.value) {
            this.setHeader(header.key, value)
        }
    }
    writeAction(responseEntity.body, this.outputStream)
}

open class PreparedHttpServletRequest(
    source: HttpServletRequest,
    parameters: Map<String, List<String>>,
    private val inputStream: ServletInputStream,
) : HttpServletRequestWrapper(source) {

    private val parameters: MutableListMap<String, String> = parameters.map { k, v ->
        k to v.toMutableList()
    }.toMutableMap().toMutableListMap()

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
        parameters.add(name, o.toString())
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