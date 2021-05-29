package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandler;

public class RuntimeExceptionStatusHandler implements ExceptionHandler<RuntimeException, ExceptionStatus> {

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull RuntimeException exception) {
        return ExceptionStatus.of("102");
    }
}
