package xyz.srclab.spring.boot.common.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionHelper {

    public static Throwable getRootCause(Throwable throwable) {
        return ExceptionUtils.getRootCause(throwable);
    }

    public static String getStackTrace(Throwable throwable) {
        return ExceptionUtils.getStackTrace(throwable);
    }
}
