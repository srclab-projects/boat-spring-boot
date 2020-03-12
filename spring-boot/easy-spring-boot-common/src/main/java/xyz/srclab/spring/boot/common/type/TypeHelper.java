package xyz.srclab.spring.boot.common.type;

public class TypeHelper {

    public static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
