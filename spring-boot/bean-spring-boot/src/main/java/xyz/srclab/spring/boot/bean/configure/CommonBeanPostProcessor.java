package xyz.srclab.spring.boot.bean.configure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import xyz.srclab.spring.boot.common.base.Ref;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;

public class CommonBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private ApplicationContext applicationContext;

    private Collection<BeanPostConfigure> beanPostConfigures = new LinkedList<>();

    @PostConstruct
    private void init() {
        beanPostConfigures.addAll(applicationContext.getBeansOfType(BeanPostConfigure.class).values());
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Ref<Object> ref = Ref.of(bean);
        for (BeanPostConfigure beanPostConfigure : beanPostConfigures) {
            Object result = beanPostConfigure.configureBeforeInitialization(new BeanPostContext() {
                @Override
                public Object getBean() {
                    return ref.get();
                }

                @Override
                public String getName() {
                    return beanName;
                }
            });
            ref.set(result);
        }
        return ref.get();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Ref<Object> ref = Ref.of(bean);
        for (BeanPostConfigure beanPostConfigure : beanPostConfigures) {
            Object result = beanPostConfigure.configureAfterInitialization(new BeanPostContext() {
                @Override
                public Object getBean() {
                    return ref.get();
                }

                @Override
                public String getName() {
                    return beanName;
                }
            });
            ref.set(result);
        }
        return ref.get();
    }
}
