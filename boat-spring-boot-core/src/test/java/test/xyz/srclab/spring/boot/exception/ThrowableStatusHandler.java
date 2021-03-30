package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStatusHandler;

@Component
public class ThrowableStatusHandler implements ExceptionStatusHandler<Throwable, ExceptionStatus> {

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
