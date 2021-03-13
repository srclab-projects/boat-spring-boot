package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.common.state.State;
import xyz.srclab.spring.boot.exception.ExceptionStateHandler;

public class TestExceptionStateHandler implements ExceptionStateHandler<ExceptionStatus> {
    @NotNull
    @Override
    public Class<?> supportedExceptionType() {
        return null;
    }

    @NotNull
    @Override
    public State handle(@NotNull Throwable throwable) {
        return null;
    }
}
