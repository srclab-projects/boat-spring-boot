package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandler;

public class ThrowableExceptionHandler implements ExceptionHandler<Throwable, String, String, ExceptionStatus> {

    @NotNull
    @Override
    public Class<?> supportedExceptionType() {
        return Throwable.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
