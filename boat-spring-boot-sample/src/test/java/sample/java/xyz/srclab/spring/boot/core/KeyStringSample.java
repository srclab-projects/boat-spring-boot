package sample.java.xyz.srclab.spring.boot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.codec.aes.AesKeys;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

import javax.annotation.Resource;
import javax.crypto.SecretKey;

@SpringBootTest(classes = {
    BoatAutoConfiguration.class,
    TestKeyString.class
})
public class KeyStringSample extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(KeyStringSample.class);

    @Resource
    private TestKeyString testProperties;

    @Test
    public void testEncodeString() {
        log.info("encodeString: {}", testProperties.keyString);
        SecretKey key = AesKeys.newKey("123");
        Assert.assertEquals(testProperties.getKeyString().decodeString(key), "some password");

        log.info("testProperties.getEncodeString(): {}", testProperties.getKeyString());
        Assert.assertEquals(testProperties.getKeyString().decodeString(key), "some password");
    }
}
