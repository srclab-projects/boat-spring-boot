package xyz.srclab.spring.boot.common.base;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

public class FastFormat {

    public static String format(String pattern, Object... args) {
        return new FastFormat(pattern, args).format();
    }

    private final String pattern;
    private final Object[] args;

    private String message;

    public FastFormat(String pattern, Object... args) {
        this.pattern = pattern;
        this.args = args;
    }

    @Override
    public String toString() {
        return format();
    }

    private String format() {
        if (message == null) {
            message = doFormat();
        }
        return message;
    }

    private String doFormat() {
        processArguments();
        return MessageFormatter.arrayFormat(pattern, args, null).getMessage();
    }

    private void processArguments() {
        if (ArrayUtils.isEmpty(args)) {
            return;
        }
        final Object lastEntry = args[args.length - 1];
        if (lastEntry instanceof Throwable) {
            args[args.length - 1] = lastEntry.toString();
        }
    }
}
