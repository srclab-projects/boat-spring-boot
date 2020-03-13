package xyz.srclab.spring.boot.common.base;

public class StringHelper {

    public static String fastFormat(String messagePattern, Object... args) {
        return FastFormat.format(messagePattern, args);
    }
}
