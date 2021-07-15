package test.xyz.srclab.spring.boot.exception;

import org.jetbrains.annotations.NotNull;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent;
import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod;

@ExceptionHandlingComponent
public class ExceptionHandler {

    @ExceptionHandlingMethod
    public ExceptionStatus handle(@NotNull RuntimeException exception) {
        return ExceptionStatus.of("102");
    }

    @ExceptionHandlingMethod
    public ExceptionStatus handle(@NotNull Throwable throwable) {
        return ExceptionStatus.of("101");
    }
}
