package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStateHandler;

@Component
public class RuntimeExceptionExceptionStateHandler implements ExceptionStateHandler<RuntimeException, ExceptionStatus> {

    @NotNull
    @Override
    public Class<RuntimeException> supportedExceptionType() {
        return RuntimeException.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull RuntimeException exception) {
        return ExceptionStatus.of("102");
    }
}
