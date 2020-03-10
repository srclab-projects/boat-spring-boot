package xyz.srclab.spring.boot.common.base;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

public class FastFormat {

    private final String pattern;
    private final Object[] args;

    private String message;

    public FastFormat(String pattern, Object... args) {
        this.pattern = pattern;
        this.args = args;
    }

    @Override
    public String toString() {
        if (message == null) {
            message = format();
        }
        return message;
    }

    private String format() {
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
