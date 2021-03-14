package test.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStatusHandler;

@Component
public class RuntimeExceptionStatusHandler implements ExceptionStatusHandler<RuntimeException, ExceptionStatus> {

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
