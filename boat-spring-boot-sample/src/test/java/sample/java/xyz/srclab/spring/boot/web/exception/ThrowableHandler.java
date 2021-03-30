package sample.java.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionHandler;

@Component
public class ThrowableHandler implements WebExceptionHandler<Throwable> {

    @NotNull
    @Override
    public Class<Throwable> supportedExceptionType() {
        return Throwable.class;
    }

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull Throwable throwable) {
        return new ResponseEntity<>(ExceptionStatus.of("101"), HttpStatus.OK);
    }
}
