package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStateHandler;

public class ExceptionExceptionStateHandler implements ExceptionStateHandler<Exception, ExceptionStatus> {

    @NotNull
    @Override
    public Class<?> supportedExceptionType() {
        return Exception.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Exception exception) {
        return ExceptionStatus.of("102");
    }
}
