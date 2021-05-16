package test.xyz.srclab.spring.boot.lang;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.lang.EncodeString;

@ConfigurationProperties(prefix = "properties")
@Component
public class TestProperties {

    private EncodeString encodeString;

    public EncodeString getEncodeString() {
        return encodeString;
    }

    public void setEncodeString(EncodeString encodeString) {
        this.encodeString = encodeString;
    }
}
