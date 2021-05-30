package sample.java.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;

public class ThrowableHandler implements WebExceptionResponseHandler<Throwable> {

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull Throwable throwable) {
        return new ResponseEntity<>(ExceptionStatus.of("101"), HttpStatus.OK);
    }
}
