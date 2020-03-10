package xyz.srclab.spring.boot.common.exception;

public class ExceptionWrapper extends RuntimeException {

    public ExceptionWrapper(Throwable wrapped) {
        super(wrapped);
    }

    public Throwable getWrapped() {
        return getCause();
    }
}
