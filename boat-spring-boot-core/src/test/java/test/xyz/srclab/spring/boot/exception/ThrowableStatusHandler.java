package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandler;

public class ThrowableStatusHandler implements ExceptionHandler<Throwable, ExceptionStatus> {

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
