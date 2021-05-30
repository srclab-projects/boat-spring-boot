package test.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;
import xyz.srclab.spring.boot.web.exception.WebStatusException;

public class WebStatusExceptionHandler implements WebExceptionResponseHandler<WebStatusException> {

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull WebStatusException exception) {
        return new ResponseEntity<>(ExceptionStatus.of("103"), exception.httpStatus());
    }
}
