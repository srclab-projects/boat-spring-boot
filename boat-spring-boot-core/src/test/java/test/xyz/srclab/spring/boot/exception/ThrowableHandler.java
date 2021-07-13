package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent;

public class ThrowableHandler implements ExceptionHandlingComponent<Throwable, ExceptionStatus> {

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
