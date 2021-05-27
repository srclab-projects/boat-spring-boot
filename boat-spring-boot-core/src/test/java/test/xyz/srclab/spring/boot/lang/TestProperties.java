package test.xyz.srclab.spring.boot.lang;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.lang.KeyString;

@ConfigurationProperties(prefix = "properties")
@Component
public class TestProperties {

    private KeyString keyString;

    public KeyString getEncodeString() {
        return keyString;
    }

    public void setEncodeString(KeyString keyString) {
        this.keyString = keyString;
    }
}
