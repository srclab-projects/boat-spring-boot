package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.exception.ExceptionWrapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class CommonBeanResolver implements BeanResolver {

    public static final CommonBeanResolver INSTANCE = new CommonBeanResolver();

    private static final ThreadLocal<WeakHashMap<Class<?>, BeanDescriptor>> descriptorCache =
            ThreadLocal.withInitial(WeakHashMap::new);

    @Override
    public boolean supportBean(Object bean) {
        return true;
    }

    @Override
    public BeanDescriptor resolve(Object bean) {
        return descriptorCache.get().computeIfAbsent(bean.getClass(), type -> {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(type);
                return new BeanDescriptorImpl(type, buildProperties(beanInfo.getPropertyDescriptors()));
            } catch (IntrospectionException e) {
                throw new ExceptionWrapper(e);
            }
        });
    }

    private Map<String, BeanPropertyDescriptor> buildProperties(PropertyDescriptor[] descriptors) {
        Map<String, BeanPropertyDescriptor> map = new HashMap<>();
        for (PropertyDescriptor descriptor : descriptors) {
            map.put(descriptor.getName(), new CommonBeanPropertyDescriptor(descriptor));
        }
        return map;
    }

    private static class CommonBeanPropertyDescriptor implements BeanPropertyDescriptor {

        private final PropertyDescriptor descriptor;

        private CommonBeanPropertyDescriptor(PropertyDescriptor descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        public String getName() {
            return descriptor.getName();
        }

        @Override
        public Class<?> getType() {
            return descriptor.getPropertyType();
        }

        @Override
        public boolean isReadable() {
            return descriptor.getReadMethod() != null;
        }

        @Override
        public Object getValue(Object bean) throws UnsupportedOperationException {
            if (!isReadable()) {
                throw new UnsupportedOperationException("Property is not readable: " + descriptor);
            }
            Method method = descriptor.getReadMethod();
            try {
                return method.invoke(bean);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e) {
                throw new ExceptionWrapper(e.getTargetException());
            }
        }

        @Override
        public boolean isWriteable() {
            return descriptor.getWriteMethod() != null;
        }

        @Override
        public void setValue(Object bean, Object value) throws UnsupportedOperationException {
            if (!isWriteable()) {
                throw new UnsupportedOperationException("Property is not writeable: " + descriptor);
            }
            Method method = descriptor.getWriteMethod();
            try {
                method.invoke(bean, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e) {
                throw new ExceptionWrapper(e.getTargetException());
            }
        }
    }
}