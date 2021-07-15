package test.xyz.srclab.spring.boot.web.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent;
import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod;
import xyz.srclab.spring.boot.web.exception.WebStatusException;

@ExceptionHandlingComponent
public class ExceptionHandler {

    @ExceptionHandlingMethod
    public Object handle(@NotNull IllegalStateException illegalStateException) {
        return ExceptionStatus.of("101");
    }

    @ExceptionHandlingMethod
    public Object handle(@NotNull RuntimeException runtimeException) {
        return new WebStatusException("102", "desc", null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandlingMethod
    public Object handle(@NotNull Exception exception) {
        return new ResponseEntity<>("103", HttpStatus.OK);
    }
}
