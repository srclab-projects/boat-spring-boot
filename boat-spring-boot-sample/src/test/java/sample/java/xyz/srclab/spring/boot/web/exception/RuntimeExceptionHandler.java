package sample.java.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;

public class RuntimeExceptionHandler implements WebExceptionResponseHandler<RuntimeException> {

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull RuntimeException exception) {
        return new ResponseEntity<>(ExceptionStatus.of("102"), HttpStatus.OK);
    }
}
