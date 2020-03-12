package xyz.srclab.spring.boot.common.proxy;

public interface MethodProxy {

    Object invoke();

    Object invoke(Object[] args);
}
