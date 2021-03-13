package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStateHandler;

public class ThrowableExceptionStateHandler implements ExceptionStateHandler<Throwable, ExceptionStatus> {

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
