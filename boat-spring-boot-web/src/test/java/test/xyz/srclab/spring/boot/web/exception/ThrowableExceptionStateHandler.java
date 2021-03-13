package test.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStateHandler;

@Component
public class ThrowableExceptionStateHandler implements ExceptionStateHandler<Throwable, ExceptionStatus> {

    @NotNull
    @Override
    public Class<Throwable> supportedExceptionType() {
        return Throwable.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
