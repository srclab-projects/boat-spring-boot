package test.xyz.srclab.spring.boot.core;

import org.springframework.beans.factory.annotation.Value;
import xyz.srclab.spring.boot.core.KeyString;

public class TestKeyString {

    @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
    KeyString keyString;

    public KeyString getKeyString() {
        return keyString;
    }

    public void setKeyString(KeyString keyString) {
        this.keyString = keyString;
    }
}
