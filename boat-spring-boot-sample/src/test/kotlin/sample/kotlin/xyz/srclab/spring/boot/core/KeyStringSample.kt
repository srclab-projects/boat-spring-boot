package sample.kotlin.xyz.srclab.spring.boot.core

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.codec.aes.toAesKey
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.core.KeyString
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, TestKeyString::class]
)
open class KeyStringSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var testProperties: TestKeyString

    @Test
    fun testEncodeString() {
        log.info("encodeString: {}", testProperties.keyString)
        val key = "123".toAesKey()
        Assert.assertEquals(testProperties.keyString.decodeString(key), "some password")
        log.info("testProperties.getEncodeString(): {}", testProperties.keyString)
        Assert.assertEquals(testProperties.keyString.decodeString(key), "some password")
    }

    companion object {
        private val log = LoggerFactory.getLogger(KeyStringSample::class.java)
    }
}

open class TestKeyString {
    @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
    lateinit var keyString: KeyString
}