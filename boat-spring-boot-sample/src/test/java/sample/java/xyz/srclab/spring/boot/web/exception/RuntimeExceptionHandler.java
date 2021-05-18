package sample.java.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;

@Component
public class RuntimeExceptionHandler implements WebExceptionResponseHandler<RuntimeException> {

    @NotNull
    public Class<RuntimeException> supportedType() {
        return RuntimeException.class;
    }

    @NotNull
    @Override
    public ResponseEntity<ExceptionStatus> handle(@NotNull RuntimeException exception) {
        return new ResponseEntity<>(ExceptionStatus.of("102"), HttpStatus.OK);
    }
}
