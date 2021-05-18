package sample.java.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandler;

@Component
public class ThrowableStatusHandler implements ExceptionHandler<Throwable, ExceptionStatus> {

    @NotNull
    public Class<Throwable> supportedType() {
        return Throwable.class;
    }

    @NotNull
    @Override
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
