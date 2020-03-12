package xyz.srclab.spring.boot.common.base;

public class StringHelper {

    public static String fastFormat(String messagePattern, Object... args) {
        return new FastFormat(messagePattern, args).toString();
    }
}
