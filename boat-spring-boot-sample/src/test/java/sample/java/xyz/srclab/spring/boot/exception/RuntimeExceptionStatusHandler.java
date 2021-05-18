package sample.java.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandler;

@Component
public class RuntimeExceptionStatusHandler implements ExceptionHandler<RuntimeException, ExceptionStatus> {

    @NotNull
    public Class<RuntimeException> supportedType() {
        return RuntimeException.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull RuntimeException exception) {
        return ExceptionStatus.of("102");
    }
}
