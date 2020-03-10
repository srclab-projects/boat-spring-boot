package xyz.srclab.spring.boot.bean.configure;

public interface BeanPostConfigure {

    Object configureBeforeInitialization(BeanPostContext context);

    Object configureAfterInitialization(BeanPostContext context);
}
