package test.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;
import xyz.srclab.spring.boot.web.exception.WebStatusException;

@Component
public class WebStatusExceptionHandler implements WebExceptionResponseHandler<WebStatusException> {

    @NotNull
    public Class<WebStatusException> supportedType() {
        return WebStatusException.class;
    }

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull WebStatusException exception) {
        return new ResponseEntity<>(ExceptionStatus.of("103"), exception.httpStatus());
    }
}
